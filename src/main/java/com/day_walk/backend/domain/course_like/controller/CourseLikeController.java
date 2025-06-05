package com.day_walk.backend.domain.course_like.controller;

import com.day_walk.backend.domain.course.data.dto.out.GetCourseByLikeDto;
import com.day_walk.backend.domain.course_like.data.in.DeleteCourseLikeDto;
import com.day_walk.backend.domain.course_like.data.in.SaveCourseLikeDto;
import com.day_walk.backend.domain.course_like.service.CourseLikeService;
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
@RequestMapping("/api/course-like")
@RestController
@Tag(name = "CourseLike 관련 API", description = "CourseLike 관련 API 명세서입니다.")
public class CourseLikeController {
    private final CourseLikeService courseLikeService;

    @Operation(summary = "코스 찜 저장", description = "한 명의 유저가 하나의 코스를 찜합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCourseLike(@RequestBody SaveCourseLikeDto saveCourseLikeDto) {
        boolean success = courseLikeService.saveCourseLike(saveCourseLikeDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 코스 찜 저장 성공!" : "유저별 코스 찜 저장 실패..");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "코스 찜 취소", description = "한 명의 유저가 하나의 코스를 찜 취소합니다.")
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteCourseLike(@RequestBody DeleteCourseLikeDto deleteCourseLikeDto) {
        boolean success = courseLikeService.deleteCourseLike(deleteCourseLikeDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 코스 찜 저장 취소 성공!" : "유저별 코스 찜 저장 취소 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "유저별 찜한 코스 전체 조회", description = "한 명의 유저가 찜한 모든 코스를 조회합니다.")
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCourseLike(@RequestParam("userId") UUID userId) {
        List<PageDto<GetCourseByLikeDto>> courseList = courseLikeService.getCourseLike(userId);
        boolean success = courseList == null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저별 찜한 코스 조회 성공!" : "유저별 찜한 코스 조회 실패..");
        response.put("courseList", courseList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
