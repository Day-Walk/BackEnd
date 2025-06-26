package com.day_walk.backend.domain.user.controller;

import com.day_walk.backend.domain.user.data.in.SaveUserDto;
import com.day_walk.backend.domain.user.data.in.SignInUserDto;
import com.day_walk.backend.domain.user.data.in.UpdateUserDto;
import com.day_walk.backend.domain.user.data.out.GetUserBySignInDto;
import com.day_walk.backend.domain.user.data.out.GetUserDto;
import com.day_walk.backend.domain.user.service.UserService;
import com.day_walk.backend.global.token.GenerateCookie;
import com.day_walk.backend.global.token.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User 관련 API", description = "User 관련된 API 명세서입니다.")
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final GenerateCookie generateCookie;

    @Operation(summary = "유저 상세 조회", description = "한 유저의 상세 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestParam("userId") UUID userId) {

        GetUserDto userInfo = userService.getUserInfo(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", userInfo != null);
        response.put("message", userInfo == null ? "유저조회 실패.." : "유저조회 성공");
        response.put("userInfo", userInfo);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "유저 초기 정보 저장", description = "초기 회원이 작성한 정보를 저장합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveUserInfo(@RequestBody SaveUserDto userInfo) {

        UUID userId = userService.saveUserInfo(userInfo);

        Map<String, Object> response = new HashMap<>();
        response.put("success", userId != null);
        response.put("message", userId == null ? "유저 초기정보 저장 실패.." : "유저 초기정보 저장 성공");
        response.put("userId", userId);


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "유저 수정", description = "한 유저의 정보를 수정합니다.")
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody UpdateUserDto updateUserDto) {

        UUID userId = userService.updateUserInfo(updateUserDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", userId != null);
        response.put("message", userId == null ? "유저 수정 실패.." : "유저 수정 성공");
        response.put("userId", userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "로그인 및 회원가입", description = "최초 로그인 시 회원가입이 됩니다.")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody SignInUserDto signInDto, HttpServletResponse httpResponse) {
        GetUserBySignInDto getUserBySignInDto = userService.signIn(signInDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", getUserBySignInDto != null);
        response.put("message", getUserBySignInDto == null ? "로그인 실패.." : "로그인 성공");
        response.put("userInfo", getUserBySignInDto);

        if (getUserBySignInDto != null) {
            ResponseCookie accessCookie = generateCookie.exec("accessToken", jwtUtil.generateAccessToken(getUserBySignInDto.getUserId(), userService.getUserRole(getUserBySignInDto.getUserId())));
            ResponseCookie refreshCookie = generateCookie.exec("refreshToken", jwtUtil.generateRefreshToken(getUserBySignInDto.getUserId()));

            httpResponse.addHeader("Set-Cookie", accessCookie.toString());
            httpResponse.addHeader("Set-Cookie", refreshCookie.toString());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "로그아웃", description = "유저가 서비스를 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse httpResponse) {
        ResponseCookie accessCookie = generateCookie.exec("accessToken");
        ResponseCookie refreshCookie = generateCookie.exec("refreshToken");

        httpResponse.addHeader("Set-Cookie", accessCookie.toString());
        httpResponse.addHeader("Set-Cookie", refreshCookie.toString());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "로그아웃 성공!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "권한 확인", description = "유저의 권한을 확인합니다.")
    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> auth() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "권한이 있습니다!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
