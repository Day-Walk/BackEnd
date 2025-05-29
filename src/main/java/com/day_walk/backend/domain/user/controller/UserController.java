package com.day_walk.backend.domain.user.controller;

import com.day_walk.backend.domain.user.data.dto.in.SaveUserDto;
import com.day_walk.backend.domain.user.data.dto.in.UpdateUserDto;
import com.day_walk.backend.domain.user.data.dto.out.GetUserDto;
import com.day_walk.backend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User 관련 API", description = "User에 관련된 API들입니다.")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestParam("userId") UUID userId) {

        GetUserDto userInfo = userService.getUserInfo(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("userInfo", userInfo);
        response.put("message", userInfo == null ? "유저조회 실패.." : "유저조회 성공");
        response.put("hasSuccess", userInfo != null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveUserInfo(@RequestBody SaveUserDto userInfo) {
        UUID userId = userService.saveUserInfo(userInfo);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("message", userId == null ? "유저 초기정보 저장 실패.." : "유저 초기정보 저장 성공");
        response.put("hasSuccess", userId != null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody UpdateUserDto updateUserDto) {

        UUID userId = userService.updateUserInfo(updateUserDto);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("message", userId == null ? "유저 수정 실패.." : "유저 수정 성공");
        response.put("hasSuccess", userId != null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
