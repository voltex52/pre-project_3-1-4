INSERT INTO roles(id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');
INSERT INTO users (id, age, username, firstname, lastname, password) VALUES (1, 27, 'admin', 'Никита', 'Леонов', 'admin');
INSERT INTO users_roles VALUES (1, 1);