package com.omar.portal.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${app.jwt.secret}")
    private String secret;
    @Value("${app.jwt.access-expiration-seconds:900}")
    private long accessExpiration;
    @Value("${app.jwt.refresh-expiration-seconds:604800}")
    private long refreshExpiration;

    public String generateAccessToken(Long userId, String role) {
        return token(userId, role, accessExpiration, "access");
    }

    public String generateRefreshToken(Long userId, String role) {
        return token(userId, role, refreshExpiration, "refresh");
    }

    private String token(Long userId, String role, long expSec, String type) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claims(Map.of("role", role, "type", type))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expSec)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Claims parse(String jwt) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
