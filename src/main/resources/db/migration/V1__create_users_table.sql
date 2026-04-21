CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users (
  id            UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
  email         VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  name          VARCHAR(100) NOT NULL,
  currency      VARCHAR(3)   NOT NULL DEFAULT 'MXN',
  created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);