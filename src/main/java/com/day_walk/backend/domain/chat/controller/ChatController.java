package com.day_walk.backend.domain.chat.controller;

import com.day_walk.backend.domain.chat.service.ChatService;
import com.day_walk.backend.domain.chat.service.SseEmitters;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RequestMapping("/api/chat")
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatService chatService;
    private final SseEmitters sseEmitters;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestParam("userId") UUID userId) {
        return ResponseEntity.ok(sseEmitters.add(userId));
    }
}
