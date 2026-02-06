package com.omar.portal.tracking;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private static final int CAPACITY = 10;
    private static final int REFILL_PER_MIN = 10;

    public synchronized boolean allow(String key) {
        Bucket b = buckets.computeIfAbsent(key, k -> new Bucket(CAPACITY, Instant.now()));
        long mins = java.time.Duration.between(b.lastRefill(), Instant.now()).toMinutes();
        if (mins > 0) {
            int refill = (int) (mins * REFILL_PER_MIN);
            b.tokens(Math.min(CAPACITY, b.tokens() + refill));
            b.lastRefill(Instant.now());
        }
        if (b.tokens() <= 0) return false;
        b.tokens(b.tokens() - 1);
        return true;
    }

    static final class Bucket {
        private int tokens;
        private Instant lastRefill;
        Bucket(int tokens, Instant lastRefill) { this.tokens = tokens; this.lastRefill = lastRefill; }
        int tokens() { return tokens; }
        void tokens(int v) { tokens = v; }
        Instant lastRefill() { return lastRefill; }
        void lastRefill(Instant v) { lastRefill = v; }
    }
}
