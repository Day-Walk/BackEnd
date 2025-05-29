package com.day_walk.backend.domain.review.controller;

import com.day_walk.backend.domain.review.data.in.DeleteReviewDto;
import com.day_walk.backend.domain.review.data.in.SaveReviewDto;
import com.day_walk.backend.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/review")
@RestController
@Tag(name = "Review 관련 API", description = "Review 관련 API 명세서입니다.")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveReview(@RequestBody SaveReviewDto saveReviewDto) {
        UUID reviewId = reviewService.saveReview(saveReviewDto);
        boolean success = reviewId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "리뷰 저장 성공!" : "리뷰 저장 실패..");
        response.put("reviewId", reviewId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteReview(@RequestBody DeleteReviewDto deleteReviewDto) {
        boolean success = reviewService.deleteReview(deleteReviewDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "리뷰 삭제 성공!" : "리뷰 삭제 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all/user")
    public ResponseEntity<Map<String, Object>> getAllReviewByUser(@RequestParam("userId") UUID userId) {

        boolean success = true;
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "" : "");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all/place")
    public ResponseEntity<Map<String, Object>> getAllReviewByPlace(@RequestParam("placeId") UUID placeId) {

        boolean success = true;
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "" : "");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all/total")
    public ResponseEntity<Map<String, Object>> getTotalByPlace(@RequestParam("placeId") UUID placeId) {

        boolean success = true;
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "" : "");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
