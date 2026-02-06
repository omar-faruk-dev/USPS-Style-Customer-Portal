package com.omar.portal.shipments;

import com.omar.portal.users.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Page<Shipment> findByUserAndStatusContainingIgnoreCase(User user, String status, Pageable pageable);
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
}
