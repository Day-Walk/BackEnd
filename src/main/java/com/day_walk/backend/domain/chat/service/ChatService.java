package com.day_walk.backend.domain.chat.service;

import com.day_walk.backend.domain.chat.data.in.SaveChatLogDto;
import com.day_walk.backend.domain.chat.data.out.SaveChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final RestTemplate restTemplate;

    @Value("${elk-server-uri}")
    private String ELK_SERVER_URI;

    public List<SaveChatDto> chatLog(UUID userId) {
        String uri = UriComponentsBuilder
                .fromUriString(ELK_SERVER_URI + "/chatbot/log/" + userId)
                .toUriString();

        ResponseEntity<List<SaveChatDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SaveChatDto>>() {}
        );

        return response.getBody();
    }
}
