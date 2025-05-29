package com.day_walk.backend.domain.user_like.controller;

import com.day_walk.backend.domain.user_like.data.dto.in.SaveUserLikeDto;
import com.day_walk.backend.domain.user_like.service.UserLikeService;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/user-like")
@RequiredArgsConstructor
@Tag(name = "UserLike 관련 API", description = "UserLike에 관련된 API들입니다.")
public class UserLikeController {

    private final UserLikeService userLikeService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUserLike(@RequestBody SaveUserLikeDto saveUserLikeDto) {

        UUID userId = userLikeService.createUserLike(saveUserLikeDto);
        boolean success = userId != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "유저 선호 저장 성공" : "유저 선호 저장 실패..");
        response.put("userId", userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
