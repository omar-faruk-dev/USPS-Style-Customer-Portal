package com.omar.portal.tracking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RateLimiterServiceTest {
    @Test
    void deniesAfterBucketExhausted() {
        RateLimiterService service = new RateLimiterService();
        for (int i = 0; i < 10; i++) assertTrue(service.allow("ip"));
        assertFalse(service.allow("ip"));
    }
}
