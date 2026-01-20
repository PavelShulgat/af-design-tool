-- Seed admin user (demo)

UPDATE users
SET
  password_hash = '$2a$10$zK3PYK5xmtIYjOk4NW1k4e3LUoWEDvrl65EKCURroyPpzkQe/82W.',
  role = 'ADMIN',
  is_active = TRUE
WHERE email = 'admin@example.com';