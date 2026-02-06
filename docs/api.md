# API Summary

## Auth
- `POST /api/auth/register` `{ "email": "user@x.com", "password": "Secret123!" }` -> `{ "accessToken": "...", "refreshToken": "..." }`
- `POST /api/auth/login` -> same response.
- `POST /api/auth/refresh` `{ "refreshToken": "..." }` -> `{ "accessToken": "..." }`
- `POST /api/auth/logout` `{ "refreshToken": "..." }` -> `204`.

## User
- `GET /api/me` -> profile.
- `PATCH /api/me` `{ "displayName": "Omar" }` -> updated profile.

## Shipments
- `POST /api/shipments`
- `GET /api/shipments?page=0&size=10&status=CREATED`
- `GET /api/shipments/{id}`
- `PATCH /api/shipments/{id}`

## Tracking
- `GET /api/track/{trackingNumber}` (cached + rate limited)

## Admin
- `GET /api/admin/users`
- `GET /api/admin/audit`
