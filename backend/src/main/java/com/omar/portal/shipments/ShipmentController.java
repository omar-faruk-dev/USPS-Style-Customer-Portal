package com.omar.portal.shipments;

import com.omar.portal.audit.AuditService;
import com.omar.portal.users.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentRepository shipments;
    private final AuditService audit;

    public ShipmentController(ShipmentRepository shipments, AuditService audit) {
        this.shipments = shipments;
        this.audit = audit;
    }

    public record ShipmentRequest(@NotBlank String trackingNumber, String status, String origin, String destination) {}
    public record ShipmentResponse(Long id, String trackingNumber, String status, String origin, String destination) {}

    @PostMapping
    public ShipmentResponse create(@AuthenticationPrincipal User user, @RequestBody ShipmentRequest req) {
        Shipment s = new Shipment();
        s.setUser(user);
        s.setTrackingNumber(req.trackingNumber());
        s.setStatus(req.status());
        s.setOrigin(req.origin());
        s.setDestination(req.destination());
        s = shipments.save(s);
        audit.log(user.getId(), "CREATE_SHIPMENT", "SHIPMENT", s.getId().toString(), "{}");
        return toResponse(s);
    }

    @GetMapping
    public Page<ShipmentResponse> list(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String status) {
        return shipments.findByUserAndStatusContainingIgnoreCase(user, status, PageRequest.of(page, size)).map(this::toResponse);
    }

    @GetMapping("/{id}")
    public ShipmentResponse get(@PathVariable Long id) {
        return toResponse(shipments.findById(id).orElseThrow());
    }

    @PatchMapping("/{id}")
    public ShipmentResponse patch(@AuthenticationPrincipal User user, @PathVariable Long id, @RequestBody ShipmentRequest req) {
        Shipment s = shipments.findById(id).orElseThrow();
        if (req.status() != null) s.setStatus(req.status());
        if (req.origin() != null) s.setOrigin(req.origin());
        if (req.destination() != null) s.setDestination(req.destination());
        s = shipments.save(s);
        audit.log(user.getId(), "UPDATE_SHIPMENT", "SHIPMENT", id.toString(), "{}");
        return toResponse(s);
    }

    private ShipmentResponse toResponse(Shipment s) {
        return new ShipmentResponse(s.getId(), s.getTrackingNumber(), s.getStatus(), s.getOrigin(), s.getDestination());
    }
}
