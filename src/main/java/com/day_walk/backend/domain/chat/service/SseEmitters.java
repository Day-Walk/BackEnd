package com.day_walk.backend.domain.chat.service;

import com.day_walk.backend.domain.chat.data.in.GetChatByMlDto;
import com.day_walk.backend.domain.chat.data.out.GetChatDto;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByChatDto;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
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

    public void sendToClient(UUID userId, GetChatByMlDto getChatByMlDto) {
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
