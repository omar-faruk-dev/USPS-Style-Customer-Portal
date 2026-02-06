#!/usr/bin/env bash
set -euo pipefail
psql "postgresql://portal:portal@localhost:5432/portal" <<SQL
INSERT INTO users(email,password_hash,role,display_name) VALUES
('admin@portal.local','$2a$10$123456789012345678901uPGxh6lNnTY7M6xBy7h6qvJ6z2F7apf.','ADMIN','Portal Admin')
ON CONFLICT (email) DO NOTHING;
SQL
echo "Seed data loaded."
