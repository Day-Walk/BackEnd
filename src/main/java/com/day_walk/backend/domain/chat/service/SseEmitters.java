package com.day_walk.backend.domain.chat.service;

import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SseEmitters {
    private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter add(UUID userId) {
        SseEmitter old = emitters.remove(userId);

        if (old != null) {
            old.complete();
        }

        SseEmitter emitter = new SseEmitter(1000L * 60);
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
}
