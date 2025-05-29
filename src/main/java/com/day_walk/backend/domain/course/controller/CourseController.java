package com.day_walk.backend.domain.course.controller;

import com.day_walk.backend.domain.course.data.dto.in.SaveCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseDto;
import com.day_walk.backend.domain.course.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
@Tag(name = "Course 관련 API", description = "Course에 관련된 API들입니다.")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCourse(@RequestBody SaveCourseDto saveCourseDto) {
        UUID courseId = courseService.saveCourse(saveCourseDto);

        Map<String, Object> response = new HashMap<>();
        response.put("courseId", courseId);
        response.put("message", courseId == null ? "코스 저장 실패.." : "코스 저장 성공!");
        response.put("success", courseId != null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCourse(@RequestParam("courseId") UUID courseId) {
        GetCourseDto courseInfo = courseService.getCourse(courseId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", courseInfo != null);
        response.put("message", courseInfo == null ? "코스 상세조회 실패.." : "코스 상세조회 성공!");
        response.put("courseInfo", courseInfo);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
