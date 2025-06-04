package com.day_walk.backend.domain.tag.controller;

import com.day_walk.backend.domain.tag.data.out.GetTagByCategoryDto;
import com.day_walk.backend.domain.tag.data.out.GetTagByPlaceDto;
import com.day_walk.backend.domain.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredArgsConstructor
@RequestMapping("/api/tag")
@RestController
@Tag(name = "Tag 관련 API", description = "Tag 관련된 API 명세서입니다.")
public class TagController {
    private final TagService tagService;

    @Operation(summary = "카테고리별 전체 조회", description = "카테고리별 연관된 태그 전체 조회합니다.")
    @GetMapping("/all/category")
    public ResponseEntity<Map<String, Object>> getAllTagByCategory(@RequestParam("categoryId") UUID categoryId) {
        List<GetTagByCategoryDto> tagList = tagService.getTagByCategory(categoryId);
        boolean success = !tagList.isEmpty();

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "카테고리별 태그 전체 조회 성공!" : "카테고리별 태그 전체 조회 실패..");
        response.put("tagList", tagList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "장소별 태그 전체 조회", description = "하나의 장소와 연관된 태그 전체 조회합니다.")
    @GetMapping("/all/place")
    public ResponseEntity<Map<String, Object>> getAllTagByPlace(@RequestParam("placeId") UUID placeId) {
        GetTagByPlaceDto getTagByPlaceDto = tagService.getTagByPlace(placeId);
        boolean success = getTagByPlaceDto != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "장소별 태그 전체 조회 성공!" : "장소별 태그 전체 조회 실패..");
        response.put("placeInfo", getTagByPlaceDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
