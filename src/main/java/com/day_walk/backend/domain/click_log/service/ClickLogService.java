package com.day_walk.backend.domain.click_log.service;

import com.day_walk.backend.domain.click_log.data.dto.in.SaveClickLogDto;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClickLogService {

    private final RestTemplate restTemplate;

    @Value("${elk-server-uri}")
    private String ELK_SERVER_URI;

    public SaveClickLogDto saveClickLog(SaveClickLogDto saveClickLogDto) {
        String uri = UriComponentsBuilder
                .fromUriString(ELK_SERVER_URI)
                .pathSegment("click-log")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SaveClickLogDto> requestEntity = new HttpEntity<>(saveClickLogDto, headers);

        try {
            ResponseEntity<SaveClickLogDto> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    requestEntity,
                    SaveClickLogDto.class
            );

            log.info("ELK 응답 상태 코드: {}", response.getStatusCode());
            log.info("ELK 응답 본문: {}", response.getBody());

            if (response.getBody() == null) {
                log.warn("ELK 응답 body가 null입니다. 요청 URI: {}", uri);
                throw new CustomException(ErrorCode.ELK_SERVER_ERROR);
            }

            return response.getBody();

        } catch (HttpStatusCodeException e) {
            log.error("ELK 서버 HTTP 오류 발생: {}", e.getStatusCode());
            log.error("오류 응답 본문: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("ELK 서버 요청 중 예외 발생: ", e);
        }

        return null;
    }
}
