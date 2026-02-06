# usps-style-customer-portal

## 1) Overview
A full-stack USPS-style customer portal portfolio project demonstrating enterprise-grade secure authentication, shipment workflows, tracking, audit trails, caching, and operational readiness.

## 2) Key Features
- Spring Boot 3 / Java 21 backend with JWT access + refresh token flow.
- RBAC-protected admin APIs and secured user profile/shipments APIs.
- Shipment CRUD with pagination and status filtering.
- Tracking lookup with Redis caching and token bucket rate limiting.
- Audit logs for sensitive actions.
- Angular 18 standalone frontend with route guards and token refresh interceptor.
- Docker Compose local stack and GitHub Actions CI.

## 3) Architecture Overview
- Frontend Angular SPA -> Spring REST APIs.
- PostgreSQL stores users, shipments, and audit logs.
- Redis stores refresh-token state and tracking cache.
- Spring Security enforces JWT validation and role-based authorization.
- Request-correlation filter sets `X-Request-Id` for structured logs.

## 4) Tech Stack
- Backend: Java 21, Spring Boot 3.x, Spring Security, Spring Data JPA, Flyway, Redis, Actuator, OpenAPI.
- Frontend: Angular 18, RxJS, Angular Router, HttpClient interceptors.
- Datastores: PostgreSQL, Redis.
- DevOps: Docker, Docker Compose, GitHub Actions.

## 5) Repository Structure
```text
usps-style-customer-portal/
  backend/
  frontend/
  docs/
  scripts/
  .github/workflows/ci.yml
  docker-compose.yml
  README.md
```

## 6) Running Locally (Docker Compose)
```bash
docker compose up --build
```
- Frontend: http://localhost:4200
- Backend: http://localhost:8080
- Health: http://localhost:8080/actuator/health

## 7) Security Model
- BCrypt password hashing.
- JWT with role claim for authorization.
- Access + refresh token model.
- Refresh token state in Redis and explicit invalidation on logout.
- CORS configured for local Angular origin.
- Auditing for admin reads, shipment writes, and tracking lookups.

## 8) Performance Design
- Redis cache for tracking endpoint.
- Token bucket limiting for `/api/track/{trackingNumber}`.
- DB indexes on shipment tracking number and user foreign key.
- Pagination for shipment listing.

## 9) CI/CD Pipeline
GitHub Actions pipeline on push/PR:
1. Build + test backend with Java 21 (`mvn test`).
2. Build frontend with Node 20 and Angular CLI.

## 10) Testing Strategy
- MockMvc controller tests for auth and shipments.
- Unit test for rate limiter behavior.
- Testcontainers-based integration test scaffold for Postgres.

## 11) Roadmap
- Add shipment event timeline and webhooks.
- Add MFA and email verification.
- Add distributed rate limiting backed by Redis.
- Add Terraform and deployment manifests.

## 12) License
MIT (see [LICENSE](LICENSE)).
