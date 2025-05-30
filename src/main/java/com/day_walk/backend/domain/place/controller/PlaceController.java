package com.day_walk.backend.domain.place.controller;

import com.day_walk.backend.domain.place.data.in.SavePlaceListDto;
import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import com.day_walk.backend.domain.place.data.out.GetSearchPlaceDto;
import com.day_walk.backend.domain.place.service.PlaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/place")
@RestController
@Tag(name = "Place 관련 API", description = "Place에 관련된 API들입니다.")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchPlace(@RequestParam("searchStr") String searchStr) {
        List<GetSearchPlaceDto> placeList = placeService.searchPlace(searchStr);
        boolean success = placeList != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "장소 검색 성공!" : "장소 검색 실패..");
        response.put("placeList", placeList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> savePlace(@RequestBody SavePlaceListDto savePlaceListDto) {
        boolean success = placeService.savePlace(savePlaceListDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "장소 저장 성공!" : "장소 저장 실패..");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
