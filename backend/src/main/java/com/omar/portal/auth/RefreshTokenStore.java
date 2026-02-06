package com.omar.portal.auth;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenStore {
    private final StringRedisTemplate redis;

    public RefreshTokenStore(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public void save(String token, Long userId) {
        redis.opsForValue().set("refresh:" + token, userId.toString(), Duration.ofDays(7));
    }

    public boolean isValid(String token) {
        return Boolean.TRUE.equals(redis.hasKey("refresh:" + token));
    }

    public void invalidate(String token) {
        redis.delete("refresh:" + token);
    }
}
