# CREACION BASE DATOS

DROP DATABASE IF EXISTS fnc;
CREATE DATABASE fnc;

# USO BASE DE DATOS 

use fnc;

# CREACION TABLAS 

CREATE TABLE usuarios(
idUsuario int auto_increment PRIMARY KEY NOT NULL,
usuario varchar (255) NOT NULL,
nick varchar (255) NOT NULL,
contraseña varchar (255) NOT NULL,
email varchar (255) NOT NULL,
telefono varchar (20) NOT NULL        
); 

CREATE TABLE insignias (
    idInsignia INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    srcImagen VARCHAR(255) NOT NULL
);

CREATE TABLE logros (
    idLogro INT AUTO_INCREMENT PRIMARY KEY,
    nombreReto VARCHAR(150) NOT NULL,
    descripcion TEXT,
    objetivo INT NOT NULL,
    idInsignia INT,
    
    FOREIGN KEY (idInsignia) 
    REFERENCES insignias(idInsignia)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

INSERT INTO insignias (nombre, srcImagen) VALUES 
('Cinéfilo Inicial', '/img/insignias/insignia1.png'),
('Cinéfilo Intermedio', '/img/insignias/insignia2.png'),
('Desafiante', '/img/insignias/insignia3.png'),
('Explorador de Ciencia Ficción', '/img/insignias/insignia4.png'),
('Fan de la Comedia', '/img/insignias/insignia5.png'),
('Amante del Drama', '/img/insignias/insignia6.png'),
('Aventura sin límites', '/img/insignias/insignia7.png'),
('Seguidor de Denis Villeneuve', '/img/insignias/insignia8.png'),
('Seguidor de Christopher Nolan', '/img/insignias/insignia9.png'),
('Seguidor de Greta Gerwig', '/img/insignias/insignia10.png'),
('Fan de Timothée Chalamet', '/img/insignias/insignia11.png'),
('Fan de Zendaya', '/img/insignias/insignia12.png'),
('Fan de Margot Robbie', '/img/insignias/insignia13.png'),
('Cinéfilo del 2023', '/img/insignias/insignia14.png'),
('Cinéfilo del 2022', '/img/insignias/insignia15.png'),
('Cinéfilo del 2021', '/img/insignias/insignia16.png');

INSERT INTO logros (nombreReto, descripcion, objetivo, idInsignia) VALUES 
('Ver 5 películas', 'Completa viendo 5 películas', 5, 1),
('Ver 10 películas', 'Completa viendo 10 películas', 10, 2),
('Completa 3 retos', 'Completa 3 logros en la plataforma', 3, 3),
('Explorador de Ciencia Ficción', 'Ve 3 películas de Ciencia Ficción', 3, 4),
('Fan de la Comedia', 'Ve 3 películas de Comedia', 3, 5),
('Amante del Drama', 'Ve 3 películas de Drama', 3, 6),
('Aventura sin límites', 'Ve 3 películas de Aventura', 3, 7),
('Seguidor de Denis Villeneuve', 'Ve 2 películas dirigidas por Denis Villeneuve', 2, 8),
('Seguidor de Christopher Nolan', 'Ve 2 películas dirigidas por Christopher Nolan', 2, 9),
('Seguidor de Greta Gerwig', 'Ve 2 películas dirigidas por Greta Gerwig', 2, 10),
('Fan de Timothée Chalamet', 'Ve 2 películas con Timothée Chalamet', 2, 11),
('Fan de Zendaya', 'Ve 2 películas con Zendaya', 2, 12),
('Fan de Margot Robbie', 'Ve 2 películas con Margot Robbie', 2, 13),
('Cinéfilo del 2023', 'Ve 3 películas estrenadas en 2023', 3, 14),
('Cinéfilo del 2022', 'Ve 3 películas estrenadas en 2022', 3, 15),
('Cinéfilo del 2021', 'Ve 3 películas estrenadas en 2021', 3, 16);



CREATE TABLE logros_usuario (
    idUsuario INT,
    idLogro INT,
    progreso INT DEFAULT 0,
    completado BOOLEAN DEFAULT FALSE,
    
    PRIMARY KEY (idUsuario, idLogro),

    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
        ON DELETE CASCADE,

    FOREIGN KEY (idLogro) REFERENCES logros(idLogro)
        ON DELETE CASCADE
);

-- Tablas principales
CREATE TABLE peliculas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    anio INT,
    sinapsis TEXT,
    srcImagen VARCHAR(255)
);

ALTER TABLE peliculas 
ADD COLUMN duracion INT AFTER anio,
ADD COLUMN lenguaje_orig VARCHAR(10) AFTER duracion;

CREATE TABLE actores (id INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(255) UNIQUE);
CREATE TABLE directores (id INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(255) UNIQUE);
CREATE TABLE generos (id INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100) UNIQUE);

-- Tablas intermedias (Relaciones)
CREATE TABLE pelicula_actores (pelicula_id INT, actor_id INT, FOREIGN KEY (pelicula_id) REFERENCES peliculas(id), FOREIGN KEY (actor_id) REFERENCES actores(id));
CREATE TABLE pelicula_directores (pelicula_id INT, director_id INT, FOREIGN KEY (pelicula_id) REFERENCES peliculas(id), FOREIGN KEY (director_id) REFERENCES directores(id));
CREATE TABLE pelicula_generos (pelicula_id INT, genero_id INT, FOREIGN KEY (pelicula_id) REFERENCES peliculas(id), FOREIGN KEY (genero_id) REFERENCES generos(id));

-- Catálogo base de retos
CREATE TABLE catalogo_retos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    descripcion TEXT,
    tipo ENUM('DIARIO', 'SEMANAL', 'TEMATICO'),
    progreso_objetivo INT
);

-- Instancias de retos por usuario
CREATE TABLE usuario_retos (
    usuario_id INT,
    reto_id INT,
    estado ENUM('ACEPTADO', 'COMPLETADO', 'EXPIRADO') DEFAULT 'ACEPTADO',
    progreso_actual INT DEFAULT 0,
    fecha_fin DATE,
    PRIMARY KEY (usuario_id, reto_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(idUsuario),
    FOREIGN KEY (reto_id) REFERENCES catalogo_retos(id)
);


INSERT INTO catalogo_retos (nombre, descripcion, tipo, progreso_objetivo) VALUES
('Cinéfilo Novato', 'Mira tu primera película en la plataforma.', 'DIARIO', 1),
('Maratón de Comedia', 'Mira 3 películas del género comedia.', 'SEMANAL', 3),
('Explorador de Culto', 'Mira una película de los años 70 u 80.', 'TEMATICO', 1),
('Fin de Semana Intensivo', 'Mira 5 películas durante el fin de semana.', 'SEMANAL', 5),
('Fanático del Terror', 'Mira 2 películas del género terror.', 'SEMANAL', 2),
('El Favorito del Director', 'Mira 2 películas del mismo director.', 'TEMATICO', 2),
('Versátil', 'Mira una película de 3 géneros diferentes.', 'SEMANAL', 3),
('Documentalista', 'Mira 2 documentales sobre historia o ciencia.', 'TEMATICO', 2),
('Noche de Acción', 'Mira 3 películas de acción.', 'SEMANAL', 3),
('Cine Mundial', 'Mira 2 películas no habladas en español.', 'TEMATICO', 2);

CREATE TABLE IF NOT EXISTS valoraciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pelicula_id INT NOT NULL,
    usuario_id INT NOT NULL, -- Si no tienes login, este campo puede ser opcional o usar la IP
    puntuacion TINYINT NOT NULL CHECK (puntuacion >= 1 AND puntuacion <= 5),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Relaciones (Foreign Keys)
    CONSTRAINT fk_peli_valoracion FOREIGN KEY (pelicula_id) 
        REFERENCES peliculas(id) ON DELETE CASCADE,
    
    -- Evita que un mismo usuario vote la misma película más de una vez
    UNIQUE KEY usuario_peli_unico (usuario_id, pelicula_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE valoraciones
ADD CONSTRAINT fk_valoraciones_usuarios
FOREIGN KEY (usuario_id) REFERENCES usuarios(idUsuario)
ON DELETE CASCADE
ON UPDATE CASCADE;


CREATE TABLE lista_pendientes (
    usuario_id INT NOT NULL,
    pelicula_id INT NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (usuario_id, pelicula_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(idUsuario) ON DELETE CASCADE,
    FOREIGN KEY (pelicula_id) REFERENCES peliculas(id) ON DELETE CASCADE
);

