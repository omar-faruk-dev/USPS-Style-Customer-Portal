package com.omar.portal.auth;

import com.omar.portal.common.Role;
import com.omar.portal.users.User;
import com.omar.portal.users.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final RefreshTokenStore refreshStore;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt, RefreshTokenStore refreshStore) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
        this.refreshStore = refreshStore;
    }

    public AuthDtos.TokenResponse register(AuthDtos.AuthRequest req) {
        User u = new User();
        u.setEmail(req.email());
        u.setPasswordHash(encoder.encode(req.password()));
        u.setRole(Role.USER);
        users.save(u);
        return issue(u);
    }

    public AuthDtos.TokenResponse login(AuthDtos.AuthRequest req) {
        User u = users.findByEmail(req.email()).orElseThrow();
        if (!encoder.matches(req.password(), u.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials");
        return issue(u);
    }

    private AuthDtos.TokenResponse issue(User u) {
        String access = jwt.generateAccessToken(u.getId(), u.getRole().name());
        String refresh = jwt.generateRefreshToken(u.getId(), u.getRole().name());
        refreshStore.save(refresh, u.getId());
        return new AuthDtos.TokenResponse(access, refresh);
    }

    public String refresh(String refreshToken) {
        if (!refreshStore.isValid(refreshToken)) throw new IllegalArgumentException("Refresh token invalidated");
        Claims claims = jwt.parse(refreshToken);
        if (!"refresh".equals(claims.get("type", String.class))) throw new IllegalArgumentException("Invalid token");
        return jwt.generateAccessToken(Long.valueOf(claims.getSubject()), claims.get("role", String.class));
    }

    public void logout(String refreshToken) {
        refreshStore.invalidate(refreshToken);
    }
}
