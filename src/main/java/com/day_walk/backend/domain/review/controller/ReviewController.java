package com.day_walk.backend.domain.review.controller;

import com.day_walk.backend.domain.review.data.in.DeleteReviewDto;
import com.day_walk.backend.domain.review.data.in.SaveReviewDto;
import com.day_walk.backend.domain.review.data.out.GetReviewByPlaceDto;
import com.day_walk.backend.domain.review.data.out.GetReviewByUserDto;
import com.day_walk.backend.domain.review.data.out.GetReviewTotalDto;
import com.day_walk.backend.domain.review.service.ReviewService;
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

@RequiredArgsConstructor
@RequestMapping("/api/review")
@RestController
@Tag(name = "Review 관련 API", description = "Review 관련 API 명세서입니다.")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 저장", description = "한 명의 유저가 한 장소에 대해 작성한 리뷰를 저장합니다.")
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

    @Operation(summary = "리뷰 삭제", description = "한 명의 유저가 한 장소에 대해 작성한 리뷰를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteReview(@RequestBody DeleteReviewDto deleteReviewDto) {
        boolean success = reviewService.deleteReview(deleteReviewDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "리뷰 삭제 성공!" : "리뷰 삭제 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "유저별 리뷰 전체 조회", description = "한 명의 유저가 작성한 모든 리뷰를 조회합니다.")
    @GetMapping("/all/user")
    public ResponseEntity<Map<String, Object>> getAllReviewByUser(@RequestParam("userId") UUID userId) {
        List<PageDto<GetReviewByUserDto>> reviewList = reviewService.getReviewByUser(userId);
        boolean success = reviewList != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 리뷰 전체 조회 성공!" : "유저별 리뷰 전체 조회 실패..");
        response.put("reviewList", reviewList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "장소별 리뷰 전체 조회", description = "한 장소에 대해 작성된 모든 리뷰를 조회합니다.")
    @GetMapping("/all/place")
    public ResponseEntity<Map<String, Object>> getAllReviewByPlace(@RequestParam("placeId") UUID placeId) {
        List<PageDto<GetReviewByPlaceDto>> reviewList = reviewService.getReviewByPlace(placeId);
        boolean success = reviewList != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "장소별 리뷰 전체 조회 성공!" : "장소별 리뷰 전체 조회 실패..");
        response.put("reviewList", reviewList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "장소별 리뷰 통계 조회", description = "한 장소에 대해 작성된 리뷰의 통계를 조회합니다.")
    @GetMapping("/all/total")
    public ResponseEntity<Map<String, Object>> getTotalByPlace(@RequestParam("placeId") UUID placeId) {
        GetReviewTotalDto reviewTotal = reviewService.getReviewTotal(placeId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "장소별 리뷰 통계 조회 성공!");
        response.put("reviewTotal", reviewTotal);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
