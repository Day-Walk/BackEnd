package com.day_walk.backend.global.token;

import com.day_walk.backend.domain.user.data.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    @Value("${jwt-secret-key}")
    private String SECRET_KEY;

    public String generateToken(UUID userId, UserRole role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 24 * 7);

        return Jwts.builder()
                .header()
                .type(JwsHeader.TYPE)
                .and()
                .claims()
                .subject(userId.toString())
                .issuedAt(now)
                .expiration(expiryDate)
                .add("role", role.name())
                .and()
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UUID getUserId(String token) {
        return UUID.fromString(getClaims(token).getSubject());
    }

    public UserRole getUserRole(String token) {
        return UserRole.valueOf(getClaims(token).get("role", String.class));
    }
}
