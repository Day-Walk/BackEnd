package com.day_walk.backend.domain.course.controller;

import com.day_walk.backend.domain.course.data.dto.in.ChangeBooleanDto;
import com.day_walk.backend.domain.course.data.dto.in.ModifyCourseTitleDto;
import com.day_walk.backend.domain.course.data.dto.in.SaveCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetAllCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetSearchCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetUsersAllCourseDto;
import com.day_walk.backend.domain.course.service.CourseService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
@Tag(name = "Course 관련 API", description = "Course 관련된 API 명세서입니다.")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "코스 저장", description = "하나의 코스를 저장합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCourse(@RequestBody SaveCourseDto saveCourseDto) {
        UUID courseId = courseService.saveCourse(saveCourseDto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", courseId == null ? "코스 저장 실패.." : "코스 저장 성공!");
        response.put("success", courseId != null);
        response.put("courseId", courseId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "코스 이름 수정", description = "하나의 코스 이름을 수정합니다.")
    @PutMapping("/title")
    public ResponseEntity<Map<String, Object>> modifyCourseName(@RequestBody ModifyCourseTitleDto modifyCourseTitleDto) {

        UUID courseId = courseService.modifyCourseName(modifyCourseTitleDto);

        boolean success = courseId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "코스 이름 수정 성공!" : "코스 이름 수정 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "코스 공개 여부 변경", description = "하나의 코스 공개 여부를 수정합니다.")
    @PutMapping("/visible")
    public ResponseEntity<Map<String, Object>> changeVisible(@RequestBody ChangeBooleanDto changeBooleanDto) {

        UUID courseId = courseService.changeVisible(changeBooleanDto);

        boolean success = courseId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "코스 공개 여부 변경 성공!" : "코스 공개 여부 변경 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "코스 삭제", description = "하나의 코스를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteCourse(@RequestBody ChangeBooleanDto changeBooleanDto) {

        UUID courseId = courseService.deleteCourse(changeBooleanDto);

        boolean success = courseId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "코스 삭제 성공!" : "코스 삭제 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "코스 상세 조회", description = "하나의 코스에 대한 내용을 조회합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCourse(@RequestParam("courseId") UUID courseId) {
        GetCourseDto courseInfo = courseService.getCourse(courseId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", courseInfo != null);
        response.put("message", courseInfo == null ? "코스 상세조회 실패.." : "코스 상세조회 성공!");
        response.put("courseInfo", courseInfo);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "코스 전체 조회", description = "공개된 모든 코스를 조회합니다. 이때 정렬 기준을 설정할 수 있습니다.")
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCourse(@RequestParam(value = "sort", defaultValue = "like") String sortStr, @RequestParam("userId") UUID userId) {

        List<PageDto<GetAllCourseDto>> courseList = courseService.getAllCourse(sortStr, userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", courseList != null);
        response.put("message", courseList == null ? "코스 전체조회 실패.." : "코스 전체조회 성공!");
        response.put("courseList", courseList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "유저별 코스 전체 조회", description = "한 명의 유저가 작성한 모든 코스를 조회합니다.")
    @GetMapping("/all/user")
    public ResponseEntity<Map<String, Object>> getUsersAllCourse(@RequestParam("userId") UUID userId) {
        List<PageDto<GetUsersAllCourseDto>> courseList = courseService.getUsersAllCourse(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", courseList != null);
        response.put("message", courseList == null ? "유저별 코스 조회 실패.." : "유저별 코스 조회 성공!");
        response.put("courseList", courseList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "코스 검색", description = "검색 결과로 나온 코스들을 조회합니다. 이때 정렬 기준을 설정할 수 있습니다.")
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getSearchCourse(@RequestParam("searchStr") String searchStr, @RequestParam(value = "sort", defaultValue = "like") String sortStr, @RequestParam("userId") UUID userId) {
        List<PageDto<GetSearchCourseDto>> courseList = courseService.getSearchCourse(searchStr, sortStr, userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", courseList != null);
        response.put("message", courseList == null ? "코스 검색 실패.." : "코스 검색 성공!");
        response.put("courseList", courseList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
