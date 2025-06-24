package com.day_walk.backend.domain.crowd_level.controller;

import com.day_walk.backend.domain.crowd_level.data.out.GetCrowdLevelDto;
import com.day_walk.backend.domain.crowd_level.service.CrowdLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/crowd")
@RequiredArgsConstructor
@Tag(name = "혼잡도 관련 API", description = "혼잡도 관련된 API 명세서입니다.")
public class CrowdLevelController {

    private final CrowdLevelService crowdLevelService;

    @Operation(summary = "혼잡도 예측", description = "시간별 혼잡도 예측도를 확인합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCrowdLevel(@RequestParam("hour") int hour) {

        GetCrowdLevelDto getCrowdLevelDto = crowdLevelService.getCrowdLevel(hour);

        Map<String, Object> response = new HashMap<>();
        response.put("success", getCrowdLevelDto != null);
        response.put("message", getCrowdLevelDto == null ? "혼잡도 예측 실패.." : "혼잡도 예측 성공!");
        response.put("crowdLevel", getCrowdLevelDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
