package com.day_walk.backend.domain.click_log.controller;

import com.day_walk.backend.domain.click_log.data.in.SaveClickLogDto;
import com.day_walk.backend.domain.click_log.service.ClickLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/click-log")
@RestController
@Tag(name = "Click Log 관련 API", description = "Click Log에 관련된 API 명세서입니다.")
public class ClickLogController {
    private final ClickLogService clickLogService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody SaveClickLogDto saveClickLogDto) {
        boolean success = clickLogService.save(saveClickLogDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "저장 성공!" : "저장 실패");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
