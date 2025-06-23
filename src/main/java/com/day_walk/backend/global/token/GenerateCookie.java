package com.day_walk.backend.global.token;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class GenerateCookie {
    public Cookie exec(String tokenName, String token) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(60*60*24);

        return cookie;
    }
}
