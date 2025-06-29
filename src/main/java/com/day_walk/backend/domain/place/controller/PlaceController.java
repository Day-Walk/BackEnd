package com.day_walk.backend.domain.place.controller;

import com.day_walk.backend.domain.place.data.out.GetPlaceBySearchListDto;
import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import com.day_walk.backend.domain.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/place")
@RestController
@Tag(name = "Place 관련 API", description = "Place 관련된 API 명세서입니다.")
public class PlaceController {
    private final PlaceService placeService;

    @Operation(summary = "장소 상세 조회", description = "하나의 장소에 대한 상세 내용을 조회합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPlace(@RequestParam("placeId") UUID placeId, @RequestParam("userId") UUID userId) {
        GetPlaceDto getPlaceDto = placeService.getPlace(placeId, userId);
        boolean success = getPlaceDto != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "장소 상세 조회 성공!" : "장소 상세 조회 실패..");
        response.put("placeInfo", getPlaceDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "장소 검색", description = "검색어를 토대로 장소 데이터를 조회합니다.")
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchPlace(@RequestParam("searchStr") String searchStr, @RequestParam("userId") UUID userId) {
        GetPlaceBySearchListDto getPlaceBySearchListDto = placeService.searchPlace(searchStr, userId);
        boolean success = getPlaceBySearchListDto != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "장소 검색 성공!" : "장소 검색 실패..");
        response.put("searchData", getPlaceBySearchListDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
