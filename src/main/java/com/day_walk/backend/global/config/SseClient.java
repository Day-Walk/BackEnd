package com.day_walk.backend.global.config;

import com.day_walk.backend.domain.chat.data.in.GetChatByMlDto;
import com.day_walk.backend.domain.chat.service.SseEmitters;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SseClient {
    private final ObjectMapper objectMapper;
    private final SseEmitters sseEmitters;

    private final OkHttpClient client = new OkHttpClient();

    @Value("${ml-server-uri}")
    private String ML_SERVER_URI;

    public boolean sendToMl(UUID userId, String question) {
        String url = UriComponentsBuilder
                .fromUriString(ML_SERVER_URI + "/chat/stream")
                .queryParam("userid", userId.toString())
                .queryParam("message", question)
                .build()
                .toUriString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        EventSource.Factory factory = EventSources.createFactory(client);

        factory.newEventSource(request, new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, Response response) {
                System.out.println("ML SSE 연결 성공!");
            }

            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                if ("[DONE]".equals(data)) {
                    System.out.println("ML 응답 스트림 종료 신호 수신");
                    return;
                }

                try {
                    GetChatByMlDto getChatByMlDto = objectMapper.readValue(data, GetChatByMlDto.class);
                    sseEmitters.sendToClient(userId, question, getChatByMlDto);
                } catch (IOException e) {
                    System.err.println("ML SSE 응답 파싱 실패: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                if (t != null) {
                    System.err.println("ML SSE 연결 실패 (예외): " + t.getMessage());
                } else if (response != null) {
                    System.err.println("ML SSE 연결 실패 (응답): HTTP " + response.code() + " - " + response.message());
                } else {
                    System.err.println("ML SSE 연결 실패: 알 수 없는 이유");
                }
            }
        });

        return true;
    }
}
