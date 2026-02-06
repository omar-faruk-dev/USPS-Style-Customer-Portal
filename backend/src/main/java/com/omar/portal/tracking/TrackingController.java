package com.omar.portal.tracking;

import com.omar.portal.audit.AuditService;
import com.omar.portal.shipments.ShipmentRepository;
import com.omar.portal.users.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/track")
public class TrackingController {
    private final ShipmentRepository shipments;
    private final RateLimiterService limiter;
    private final AuditService audit;

    public TrackingController(ShipmentRepository shipments, RateLimiterService limiter, AuditService audit) {
        this.shipments = shipments;
        this.limiter = limiter;
        this.audit = audit;
    }

    @GetMapping("/{trackingNumber}")
    public Map<String, String> track(@PathVariable String trackingNumber, HttpServletRequest request, @AuthenticationPrincipal User user) {
        String key = user != null ? "u:" + user.getId() : request.getRemoteAddr();
        if (!limiter.allow(key)) throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");
        Map<String, String> result = cachedLookup(trackingNumber);
        audit.log(user != null ? user.getId() : null, "TRACK_LOOKUP", "SHIPMENT", trackingNumber, "{}");
        return result;
    }

    @Cacheable(cacheNames = "tracking", key = "#trackingNumber")
    public Map<String, String> cachedLookup(String trackingNumber) {
        var shipment = shipments.findByTrackingNumber(trackingNumber).orElseThrow();
        return Map.of("trackingNumber", shipment.getTrackingNumber(), "status", shipment.getStatus(),
                "origin", shipment.getOrigin(), "destination", shipment.getDestination());
    }
}
