INSERT INTO roles(id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');
INSERT INTO users (id, age, username, firstname, lastname, password) VALUES (1, 30, 'admin', 'Никита', 'Леонов', '$2y$12$x3HPSGLtFgh3F4XmX6zgrOaaD.My2lzOjadU.JW0vCXYZVOokAW7q');
INSERT INTO users_roles VALUES (1, 1);