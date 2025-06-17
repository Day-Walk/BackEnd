package com.day_walk.backend.domain.chat.service;

import com.day_walk.backend.domain.chat.data.in.GetChatByMlDto;
import com.day_walk.backend.domain.chat.data.out.GetChatDto;
import com.day_walk.backend.domain.chat.data.out.SaveChatDto;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByChatDto;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SseEmitters {
    private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final GetPlaceEntityBean getPlaceEntityBean;

    private final RestTemplate restTemplate;

    @Value("${elk-server-uri}")
    private String ELK_SERVER_URI;

    public SseEmitter add(UUID userId) {
        SseEmitter old = emitters.remove(userId);

        if (old != null) {
            old.complete();
        }

        SseEmitter emitter = new SseEmitter(30_000L);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("SSE connection success!"));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.SSE_CONNECTION_ERROR);
        }

        return emitter;
    }

    public void sendToClient(UUID userId, String question, GetChatByMlDto getChatByMlDto) {
        SseEmitter emitter = emitters.get(userId);

        if (emitter != null) {
            GetChatDto response = GetChatDto.builder()
                    .title(getChatByMlDto.getStr1())
                    .placeList(getChatByMlDto.getPlaceid() == null
                            ? Collections.emptyList()
                            : getChatByMlDto.getPlaceid().stream()
                            .map(placeId -> {
                                PlaceEntity place = getPlaceEntityBean.exec(placeId);

                                return GetPlaceByChatDto.builder()
                                        .place(place)
                                        .build();
                            })
                            .collect(Collectors.toList()))
                    .detail(getChatByMlDto.getStr2())
                    .build();

            SaveChatDto saveChatDto = SaveChatDto.builder()
                    .userId(userId)
                    .question(question)
                    .answer(response)
                    .createAt(LocalDateTime.now())
                    .build();

            String uri = UriComponentsBuilder
                    .fromUriString(ELK_SERVER_URI + "/chatbot")
                    .toUriString();

            String success = restTemplate.postForObject(uri, saveChatDto, String.class);

            try {
                emitter.send(SseEmitter.event()
                        .name("chatbot")
                        .data(response));
            } catch (IOException e) {
                throw new CustomException(ErrorCode.SSE_CONNECTION_ERROR);
            }
        }
    }
}
