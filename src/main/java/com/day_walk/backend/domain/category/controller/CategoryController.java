package com.day_walk.backend.domain.category.controller;

import com.day_walk.backend.domain.category.data.dto.out.GetCategoryDto;
import com.day_walk.backend.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/category")
@RestController
@Tag(name = "Category 관련 API", description = "Category에 관련된 API들입니다.")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCategory() {
        List<GetCategoryDto> categoryList = categoryService.getAllCategory();

        boolean success = !categoryList.isEmpty();

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "카테고리 전체 조회 성공!" : "카테고리 전체 조회 실패..");
        response.put("categoryList", categoryList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
