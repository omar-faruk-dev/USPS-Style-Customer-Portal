# Architecture Overview

The backend follows modular package boundaries: `auth`, `users`, `shipments`, `tracking`, `admin`, `audit`, and `config`.

## Data Flow
1. Angular client authenticates via `/api/auth/*`.
2. Access token is attached by interceptor to secured APIs.
3. Spring Security verifies JWT and maps role claims.
4. Shipment and admin actions produce audit log rows.

## Caching & Rate Limiting
- Tracking lookups use Spring Cache backed by Redis to reduce DB load.
- Token-bucket rate limiter protects `/api/track/{trackingNumber}` and returns HTTP 429 on exhaustion.

## Audit Logging
Sensitive actions (shipment create/update, tracking lookup, admin reads) are persisted in `audit_logs`.
