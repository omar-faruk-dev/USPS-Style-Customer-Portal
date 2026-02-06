package com.omar.portal.audit;

import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final AuditLogRepository repo;

    public AuditService(AuditLogRepository repo) { this.repo = repo; }

    public void log(Long actorUserId, String action, String entityType, String entityId, String metaJson) {
        AuditLog l = new AuditLog();
        l.setActorUserId(actorUserId);
        l.setAction(action);
        l.setEntityType(entityType);
        l.setEntityId(entityId);
        l.setMetaJson(metaJson);
        repo.save(l);
    }
}
