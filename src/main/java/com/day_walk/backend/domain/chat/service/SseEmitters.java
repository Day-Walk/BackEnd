package com.day_walk.backend.domain.chat.service;

import com.day_walk.backend.domain.chat.data.in.GetChatByMlDto;
import com.day_walk.backend.domain.chat.data.in.SaveChatLogDto;
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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
            old.completeWithError(new CustomException(ErrorCode.SSE_CONNECTION_ERROR));
        }

        SseEmitter emitter = new SseEmitter(30_000L);
        emitters.put(userId, emitter);
        System.out.println(LocalDateTime.now());

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("SSE connection success!"));
            System.out.println(LocalDateTime.now());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.SSE_CONNECTION_ERROR);
        }

        return emitter;
    }

    public void sendToClient(UUID userId, String question, GetChatByMlDto getChatByMlDto) {
        SseEmitter emitter = emitters.get(userId);

        if (emitter != null) {
            GetChatDto response = GetChatDto.builder()
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
                    .detail(getChatByMlDto.getStr())
                    .build();

            SaveChatDto saveChatDto = SaveChatDto.builder()
                    .userId(userId)
                    .question(question)
                    .answer(response)
                    .createAt(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT))
                    .build();

            String uri = UriComponentsBuilder
                    .fromUriString(ELK_SERVER_URI + "/chatbot")
                    .toUriString();

            SaveChatLogDto saveChatLogDto = restTemplate.postForObject(uri, saveChatDto, SaveChatLogDto.class);
            System.out.println(saveChatLogDto.getMessage());

            try {
                emitter.send(SseEmitter.event()
                        .name("chatbot")
                        .data(response));
            } catch (IOException e) {
                throw new CustomException(ErrorCode.SSE_CONNECTION_ERROR);
            }
        }
    }

    public void sendToClientDone(UUID userId) {
        SseEmitter emitter = emitters.get(userId);

        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("chatbot")
                        .data("[DONE]"));

                emitter.complete();
            } catch (IOException e) {
                throw new CustomException(ErrorCode.SSE_CONNECTION_ERROR);
            }
        }
    }
}
