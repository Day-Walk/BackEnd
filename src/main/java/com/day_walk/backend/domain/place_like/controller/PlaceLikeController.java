package com.day_walk.backend.domain.place_like.controller;

import com.day_walk.backend.domain.place_like.data.in.DeletePlaceLikeDto;
import com.day_walk.backend.domain.place_like.data.in.SavePlaceLikeDto;
import com.day_walk.backend.domain.place_like.service.PlaceLikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/place-like")
@RequiredArgsConstructor
@RestController
@Tag(name = "PlaceLike 관련 API", description = "PlaceLike 관련 API 명세서입니다.")
public class PlaceLikeController {
    private final PlaceLikeService placeLikeService;

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

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deletePlaceLike(@RequestBody DeletePlaceLikeDto deletePlaceLikeDto) {
        boolean success = placeLikeService.deletePlaceLike(deletePlaceLikeDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 장소 찜 저장 취소 성공!" : "유저별 장소 찜 저장 취소 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
