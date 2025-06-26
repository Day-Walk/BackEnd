package com.day_walk.backend.global.token;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class GenerateCookie {
    public ResponseCookie exec(String tokenName, String token) {
        ResponseCookie cookie = ResponseCookie
                .from(tokenName, token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(1))
                .build();

        return cookie;
    }

    public ResponseCookie exec(String tokenName) {
        ResponseCookie cookie = ResponseCookie
                .from(tokenName, "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0) // 즉시 만료
                .build();

        return cookie;
    }
}
