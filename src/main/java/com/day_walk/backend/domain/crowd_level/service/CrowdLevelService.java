package com.day_walk.backend.domain.crowd_level.service;

import com.day_walk.backend.domain.crowd_level.data.out.GetCrowdLevelDto;
import com.day_walk.backend.domain.crowd_level.data.out.MlCrowdResponseDto;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrowdLevelService {

    private final RestTemplate restTemplate;

    @Value("${ml-server-uri}")
    private String ML_SERVER_URI;

    public GetCrowdLevelDto getCrowdLevel(int hour) {
        String uri = UriComponentsBuilder
                .fromUriString(ML_SERVER_URI + "/crowd")
                .queryParam("hour", hour)
                .toUriString();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<MlCrowdResponseDto> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    MlCrowdResponseDto.class
            );

            MlCrowdResponseDto result = response.getBody();

            if (result == null || result.getCrowdLevel() == null) {
                log.warn("ML 응답이 null이거나 crowdLevel이 없습니다.");
                throw new CustomException(ErrorCode.ML_SERVER_ERROR);
            }

            return result.getCrowdLevel();
        } catch (HttpStatusCodeException e) {
            log.error("ML 서버 HTTP 오류 발생: {}", e.getStatusCode());
            log.error("오류 응답 본문: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("ML 서버 요청 중 예외 발생: ", e);
        }
        return null;

    }
}
