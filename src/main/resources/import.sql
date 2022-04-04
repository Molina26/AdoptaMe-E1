CREATE DATABASE adoptame;

USE adoptame;

INSERT INTO role(id, name) VALUES
(1 , 'ROLE_ADMINISTRADOR'),
(2, 'ROLE_VOLUNTARIO'),
(3, 'ROLE_ADOPTADOR');

INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES
(1, '2022-03-21 10:23:34', 1,  'Martínez','Enrique', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Ojeda','admin@adoptame.com');

INSERT INTO color (name) VALUES ('Blanco'),
        ('Café'),
        ('Gris'),
        ('Negro'),
        ('Atigrado'),
        ('Bicolor'),
        ('Con manchas'),
        ('Varios colores');

INSERT INTO personality(name) VALUES ('Activo'),
        ('Independiente'),
        ('Juguetón'),
        ('Protector'),
        ('Ruidoso'),
        ('Tímido'),
        ('Tranquilo'),
        ('Amoroso');

INSERT INTO size(name) VALUES ('Pequeño') , ('Mediano'), ('Grande');

INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES
(2, '2022-03-21 10:23:34', 1,  'Ortiz','Michel', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Vega','voluntario@adoptame.com');

INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES
(3, '2022-03-21 10:23:34', 1,  'Ayala','Cynthia', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Calderon','adoptador@adoptame.com');

INSERT INTO authorities (username, authority) VALUES 
        (1,1),(2,2),(3,3);
