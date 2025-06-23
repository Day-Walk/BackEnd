package com.day_walk.backend.global.token;

import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user.data.UserRole;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final GetUserEntityBean getUserEntityBean;
    private final JwtUtil jwtUtil;
    private final GenerateCookie generateCookie;

    private String getTokenFromCookies(Cookie[] cookies, String tokenName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (tokenName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void addToken(HttpServletResponse response, String tokenName, String token) {
        ResponseCookie responseCookie = generateCookie.exec(tokenName, token);
        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String accessToken = getTokenFromCookies(request.getCookies(), "accessToken");
        final String refreshToken = getTokenFromCookies(request.getCookies(), "refreshToken");

        log.info("AccessToken from cookies : {}", accessToken);
        log.info("RefreshToken from cookies : {}", refreshToken);

        try {
            if (accessToken != null && !accessToken.isEmpty() && jwtUtil.validateToken(accessToken)) {
                authenticateUser(accessToken, request);
            }
        } catch (ExpiredJwtException e) {
            if (refreshToken != null && !refreshToken.isEmpty() && jwtUtil.validateToken(refreshToken)) {
                UUID userId = jwtUtil.getUserId(refreshToken);
                UserEntity user = getUserEntityBean.exec(userId);
                if (user == null) {
                    throw new CustomException(ErrorCode.TOKEN_ERROR);
                }

                String newAccessToken = jwtUtil.generateAccessToken(userId, user.getUserRole());
                addToken(response, "accessToken", newAccessToken);

                String newRefreshToken = jwtUtil.generateRefreshToken(userId);
                addToken(response, "refreshToken", newRefreshToken);

                authenticateUser(newAccessToken, request);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token, HttpServletRequest request) {
        UUID userId = jwtUtil.getUserId(token);
        UserRole role = jwtUtil.getUserRole(token);
        log.info("userId : {}", userId);
        log.info("role : {}", role);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
