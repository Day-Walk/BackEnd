package com.day_walk.backend.domain.course.controller;

import com.day_walk.backend.domain.course.data.dto.in.ChangeBooleanDto;
import com.day_walk.backend.domain.course.data.dto.in.ModifyCourseTitleDto;
import com.day_walk.backend.domain.course.data.dto.in.SaveCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetAllCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetUsersAllCourseDto;
import com.day_walk.backend.domain.course.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @PutMapping("/title")
    public ResponseEntity<Map<String, Object>> modifyCourseName(@RequestBody ModifyCourseTitleDto modifyCourseTitleDto) {

        UUID courseId = courseService.modifyCourseName(modifyCourseTitleDto);

        boolean success = courseId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "코스 이름 수정 성공!" : "코스 이름 수정 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/visible")
    public ResponseEntity<Map<String, Object>> changeVisible(@RequestBody ChangeBooleanDto changeBooleanDto) {

        UUID courseId = courseService.changeVisible(changeBooleanDto);

        boolean success = courseId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "코스 공개 여부 변경 성공!" : "코스 공개 여부 변경 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> deleteCourse(@RequestBody ChangeBooleanDto changeBooleanDto) {

        UUID courseId = courseService.deleteCourse(changeBooleanDto);

        boolean success = courseId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "코스 삭제 성공!" : "코스 삭제 실패..");

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

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCourse(@RequestParam("sort") String sortStr) {

        List<GetAllCourseDto> courseList = courseService.getAllCourse(sortStr);

        Map<String, Object> response = new HashMap<>();
        response.put("success", courseList != null);
        response.put("message", courseList == null ? "코스 전체조회 실패.." : "코스 전체조회 성공!");
        response.put("courseInfo", courseList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all/user")
    public ResponseEntity<Map<String, Object>> getUsersAllCourse(@RequestParam("userId")UUID userId) {
        List<GetUsersAllCourseDto> courseList = courseService.getUsersAllCourse(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", courseList != null);
        response.put("message", courseList == null ? "유저별 코스 조회 실패.." : "유저별 코스 조회 성공!");
        response.put("courseInfo", courseList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
