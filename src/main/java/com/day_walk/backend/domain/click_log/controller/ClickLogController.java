package com.day_walk.backend.domain.click_log.controller;

import com.day_walk.backend.domain.click_log.data.in.SaveClickLogDto;
import com.day_walk.backend.domain.click_log.data.out.SaveClickLogByElkDto;
import com.day_walk.backend.domain.click_log.service.ClickLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/click-log")
@Tag(name = "Click-log 관련 API", description = "Click-log 관련된 API 명세서입니다.")
@RequiredArgsConstructor
@RestController
public class ClickLogController {

    private final ClickLogService clickLogService;

    @Operation(summary = "클릭 로그 저장", description = "한 유저의 클릭 기록을 저장합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveClickLog(@RequestBody SaveClickLogDto saveClickLogDto) {
        SaveClickLogByElkDto saveResult = clickLogService.saveClickLog(saveClickLogDto);
        boolean success = saveResult != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "클릭 기록 저장 완료!" : "클릭 기록 저장 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
