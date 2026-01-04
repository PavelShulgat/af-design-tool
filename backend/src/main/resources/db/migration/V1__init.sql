-- V1__init.sql
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email TEXT UNIQUE NOT NULL,
  first_name  TEXT NOT NULL,
  last_name  TEXT NOT NULL,

  -- auth
  password_hash TEXT NOT NULL,
  role TEXT NOT NULL DEFAULT 'USER',
  is_active BOOLEAN NOT NULL DEFAULT TRUE,

  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);


INSERT INTO users (email, first_name, last_name, password_hash, role)
VALUES
  ('admin@example.com', 'Admin', '1', '$2a$10$REPLACE_WITH_REAL_BCRYPT_HASH', 'ADMIN')
ON CONFLICT (email) DO NOTHING;