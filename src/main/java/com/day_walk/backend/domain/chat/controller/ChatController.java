package com.day_walk.backend.domain.chat.controller;

import com.day_walk.backend.domain.chat.data.out.SaveChatDto;
import com.day_walk.backend.domain.chat.service.ChatService;
import com.day_walk.backend.domain.chat.service.SseEmitters;
import com.day_walk.backend.global.config.SseClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "ChatBot 관련 API", description = "ChatBot 관련된 API 명세서입니다.")
@RestController
public class ChatController {
    private final ChatService chatService;
    private final SseEmitters sseEmitters;
    private final SseClient sseClient;

    @Operation(summary = "SSE 연결 요청", description = "한 명의 유저가 서버에 SSE 연결을 요청합니다.")
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestParam("userId") UUID userId) {
        System.out.println(LocalDateTime.now());
        return ResponseEntity.ok(sseEmitters.add(userId));
    }

    @Operation(summary = "챗봇 질문", description = "한 유저가 챗봇에게 질문을 전달합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> chatting(@RequestParam("userId") UUID userId, @RequestParam("question") String question) {
        boolean success = sseClient.sendToMl(userId, question);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "질문 전송 성공!" : "질문 전송 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "채팅기록 조회", description = "한 유저의 지난 7일간 챗봇과 나눴던 채팅기록을 조회합니다.")
    @GetMapping("/log")
    public ResponseEntity<Map<String, Object>> chatLog(@RequestParam("userId") UUID userId) {
        List<SaveChatDto> chatLogList = chatService.chatLog(userId);
        boolean success = chatLogList != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "채팅기록 조회 성공!" : "채팅기록 조회 실패..");
        response.put("chatLog", success ? chatLogList : null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
