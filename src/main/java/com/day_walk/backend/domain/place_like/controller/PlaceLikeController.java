package com.day_walk.backend.domain.place_like.controller;

import com.day_walk.backend.domain.place.data.out.GetPlaceByLikeDto;
import com.day_walk.backend.domain.place_like.data.in.DeletePlaceLikeDto;
import com.day_walk.backend.domain.place_like.data.in.SavePlaceLikeDto;
import com.day_walk.backend.domain.place_like.service.PlaceLikeService;
import com.day_walk.backend.global.util.page.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/place-like")
@RequiredArgsConstructor
@RestController
@Tag(name = "PlaceLike 관련 API", description = "PlaceLike 관련 API 명세서입니다.")
public class PlaceLikeController {
    private final PlaceLikeService placeLikeService;

    @Operation(summary = "장소 찜 저장", description = "한 명의 유저가 하나의 장소를 찜 합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> savePlaceLike(@RequestBody SavePlaceLikeDto savePlaceLikeDto) {
        UUID placeLikeId = placeLikeService.savePlaceLike(savePlaceLikeDto);
        boolean success = placeLikeId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 장소 찜 저장 성공!" : "유저별 장소 찜 저장 실패..");
        response.put("placeLikeId", placeLikeId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "장소 찜 취소", description = "한 명의 유저가 하나의 장소를 찜 취소합니다.")
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deletePlaceLike(@RequestBody DeletePlaceLikeDto deletePlaceLikeDto) {
        boolean success = placeLikeService.deletePlaceLike(deletePlaceLikeDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 장소 찜 저장 취소 성공!" : "유저별 장소 찜 저장 취소 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "유저별 찜한 장소 전체 조회", description = "한 명의 유저가 찜한 모든 장소를 조회합니다.")
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getPlaceLike(@RequestParam("userId") UUID userId) {
        List<PageDto<GetPlaceByLikeDto>> placeList = placeLikeService.getPlaceLike(userId);
        boolean success = placeList != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 찜한 장소 조회 성공!" : "유저별 찜한 장소 조회 실패..");
        response.put("placeList", placeList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
