package com.day_walk.backend.domain.click_log.controller;

import com.day_walk.backend.domain.click_log.data.in.SaveClickLogDto;
import com.day_walk.backend.domain.click_log.data.out.SaveClickLogByElkDto;
import com.day_walk.backend.domain.click_log.service.ClickLogService;
import com.day_walk.backend.domain.place.data.out.GetPlaceByTop4;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @Operation(summary = "가장 많이 클릭된 장소 조회", description = "당일 새벽 5시 이후 가장 많이 클릭된 장소 Top4를 조회합니다.")
    @GetMapping("/place")
    public ResponseEntity<Map<String, Object>> getPlaceByClickLog() {
        List<GetPlaceByTop4> getPlaceByTop4List = clickLogService.getPlaceByClickLog();
        boolean success = getPlaceByTop4List != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "장소 Top4 조회 성공!" : "장소 Top4 조회 실패..");
        response.put("searchData", getPlaceByTop4List);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
