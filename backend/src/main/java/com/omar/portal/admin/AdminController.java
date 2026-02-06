package com.omar.portal.admin;

import com.omar.portal.audit.AuditLog;
import com.omar.portal.audit.AuditLogRepository;
import com.omar.portal.audit.AuditService;
import com.omar.portal.users.User;
import com.omar.portal.users.UserRepository;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserRepository users;
    private final AuditLogRepository audits;
    private final AuditService auditService;

    public AdminController(UserRepository users, AuditLogRepository audits, AuditService auditService) {
        this.users = users;
        this.audits = audits;
        this.auditService = auditService;
    }

    @GetMapping("/users")
    public List<User> users(@AuthenticationPrincipal User admin) {
        auditService.log(admin.getId(), "ADMIN_USERS", "USER", "*", "{}");
        return users.findAll();
    }

    @GetMapping("/audit")
    public List<AuditLog> audit(@AuthenticationPrincipal User admin) {
        auditService.log(admin.getId(), "ADMIN_AUDIT", "AUDIT", "*", "{}");
        return audits.findAll();
    }
}
