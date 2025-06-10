package com.day_walk.backend.domain.image.controller;

import com.day_walk.backend.domain.image.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
@Tag(name = "ImageUpload 관련 API", description = "Image 관련된 API 명세서입니다.")
public class ImageController {

    private final ImageService imageService;

    // 파일 업로드 API
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestBody MultipartFile file) {

        String imageUrl = imageService.uploadImage(file);

        Map<String, Object> response = new HashMap<>();
        response.put("message", imageUrl != null ? "이미지 저장 성공!" : "이미지 저장 실패..");
        response.put("success", imageUrl != null);
        response.put("imageUrl", imageUrl);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
