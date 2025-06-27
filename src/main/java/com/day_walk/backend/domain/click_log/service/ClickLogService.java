package com.day_walk.backend.domain.click_log.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.click_log.data.in.GetClickLogByTop4;
import com.day_walk.backend.domain.click_log.data.in.SaveClickLogDto;
import com.day_walk.backend.domain.click_log.data.out.SaveClickLogByElkDto;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByTop4;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClickLogService {

    private final RestTemplate restTemplate;
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;

    @Value("${elk-server-uri}")
    private String ELK_SERVER_URI;

    public SaveClickLogByElkDto saveClickLog(SaveClickLogDto saveClickLogDto) {
        UriComponents uri = UriComponentsBuilder
                .fromUriString(ELK_SERVER_URI + "/click-log")
                .build()
                .encode();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ZonedDateTime nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        String timestamp = nowKST.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        SaveClickLogByElkDto saveClickLogByElkDto = SaveClickLogByElkDto.builder()
                .userId(saveClickLogDto.getUserId())
                .placeId(saveClickLogDto.getPlaceId())
                .timestamp(timestamp)
                .build();

        HttpEntity<SaveClickLogByElkDto> requestEntity = new HttpEntity<>(saveClickLogByElkDto, headers);

        try {
            ResponseEntity<SaveClickLogByElkDto> response = restTemplate.exchange(
                    uri.toUri(),
                    HttpMethod.POST,
                    requestEntity,
                    SaveClickLogByElkDto.class
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

    public List<GetPlaceByTop4> getPlaceByClickLog() {
        UriComponents uri = UriComponentsBuilder
                .fromUriString(ELK_SERVER_URI + "/most-click-place")
                .build()
                .encode();

        ResponseEntity<List<GetClickLogByTop4>> response = restTemplate.exchange(
                uri.toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GetClickLogByTop4>>() {}
        );

        List<GetClickLogByTop4> body = response.getBody();
        if (body == null) {
            return null;
        }

        return body.stream()
                .map(value -> {
                    PlaceEntity place = getPlaceEntityBean.exec(value.getUuid());
                    if (place == null) {
                        return null;
                    }

                    SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(place.getSubCategoryId());
                    CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());

                    return GetPlaceByTop4.builder()
                            .place(place)
                            .subCategory(subCategory.getName())
                            .category(category.getName())
                            .clickNum(value.getClicks())
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
