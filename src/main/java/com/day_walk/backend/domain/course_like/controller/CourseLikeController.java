package com.day_walk.backend.domain.course_like.controller;

import com.day_walk.backend.domain.course_like.data.in.DeleteCourseLikeDto;
import com.day_walk.backend.domain.course_like.data.in.SaveCourseLikeDto;
import com.day_walk.backend.domain.course_like.service.CourseLikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/course-like")
@RestController
@Tag(name = "CourseLike 관련 API", description = "CourseLike 관련 API 명세서입니다.")
public class CourseLikeController {
    private final CourseLikeService courseLikeService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCourseLike(@RequestBody SaveCourseLikeDto saveCourseLikeDto) {
        UUID placeLikeId = courseLikeService.saveCourseLike(saveCourseLikeDto);
        boolean success = placeLikeId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 코스 찜 저장 성공!" : "유저별 코스 찜 저장 실패..");
        response.put("placeLikeId", placeLikeId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteCourseLike(@RequestBody DeleteCourseLikeDto deleteCourseLikeDto) {
        boolean success = courseLikeService.deleteCourseLike(deleteCourseLikeDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 코스 찜 저장 취소 성공!" : "유저별 코스 찜 저장 취소 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
