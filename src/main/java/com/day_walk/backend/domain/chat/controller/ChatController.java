package com.day_walk.backend.domain.chat.controller;

import com.day_walk.backend.domain.chat.service.SseEmitters;
import com.day_walk.backend.global.config.SseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/chat")
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final SseEmitters sseEmitters;
    private final SseClient sseClient;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestParam("userId") UUID userId) {
        return ResponseEntity.ok(sseEmitters.add(userId));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> chatting(@RequestParam("userId") UUID userId, @RequestParam("question") String question) {
        boolean success = sseClient.sendToMl(userId, question);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "질문 전송 성공!" : "질문 전송 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
