# AdoptaMe-E1

# Bienvenido a AdoptaMe

Bienvenido al sistema **AdoptaMe**, una aplicación para la adopción de mascotas.

Esta aplicación permite la publicación de mascotas en busca de un hogar con personas amantes de los gatos y/o perros, así como apoyar de forma monetaria la alimentación y cuidado de los mismos.


## Instalación del proyecto

En primer lugar, clona o descarga nuestro proyecto desde la rama dev, en tu equipo. 

## Requerimientos antes de ejecutar el proyecto

Antes de ejecutar el proyecto, crea en MySQL una base de datos con el nombre **adoptame**, a continuación te mostramos la sentencia:

> CREATE DATABASE adoptame;

Después de haber creado exitosamente la base de datos, configura el application.properties colocando los datos correspondientes de tu usuario en MySQL.

>spring.datasource.username = root
>spring.datasource.password = root

Una vez hecho lo anterior, crearemos una carpeta en el directorio principal de tu disco local, con el siguiente nombre:

> C:/images

Una vez hecho lo anterior, ejecuta el proyecto.

## Primeros registros para acceder a la app

Cuando hayas ejecutado la aplicación, es necesario ejecutar las siguientes sentencias en MySQL:

>USE adoptame;

>INSERT INTO role(id, name) VALUES
(1 , 'ROLE_ADMINISTRADOR'),
(2, 'ROLE_VOLUNTARIO'),
(3, 'ROLE_ADOPTADOR');

>INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES
(1, '2022-03-21 10:23:34', 1,  'Martínez','Enrique', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Ojeda','admin@adoptame.com');

>INSERT INTO color (name) VALUES ('Blanco'),
        ('Café'),
        ('Gris'),
        ('Negro'),
        ('Atigrado'),
        ('Bicolor'),
        ('Con manchas'),
        ('Varios colores');

>INSERT INTO personality(name) VALUES ('Activo'),
        ('Independiente'),
        ('Juguetón'),
        ('Protector'),
        ('Ruidoso'),
        ('Tímido'),
        ('Tranquilo'),
        ('Amoroso');

>INSERT INTO size(name) VALUES ('Pequeño') , ('Mediano'), ('Grande');

>INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES
(2, '2022-03-21 10:23:34', 1,  'Ortiz','Michel', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Vega','voluntario@adoptame.com');

>INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES
(3, '2022-03-21 10:23:34', 1,  'Ayala','Cynthia', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Calderon','adoptador@adoptame.com');

>INSERT INTO authorities (username, authority) VALUES 
        (1,1),(2,2),(3,3);

Con lo anterior podrás acceder mediante el usuario y contraseña "adno12345".
