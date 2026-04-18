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
    tipo_requisito ENUM('general', 'genero', 'director', 'actor', 'anio') DEFAULT 'general',
    valor_requisito VARCHAR(100) NULL, 
    
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

INSERT INTO logros (nombreReto, descripcion, objetivo, idInsignia, tipo_requisito, valor_requisito) VALUES 
('Ver 5 películas', 'Completa viendo 5 películas', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Cinéfilo Inicial'), 'general', NULL),
('Ver 10 películas', 'Completa viendo 10 películas', 10, (SELECT idInsignia FROM insignias WHERE nombre = 'Cinéfilo Intermedio'), 'general', NULL),
('Completa 3 retos', 'Completa 3 logros en la plataforma', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Desafiante'), 'general', NULL),
('Explorador de Ciencia Ficción', 'Ve 3 películas de Ciencia Ficción', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Explorador de Ciencia Ficción'), 'genero', 'Ciencia Ficción'),
('Fan de la Comedia', 'Ve 3 películas de Comedia', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de la Comedia'), 'genero', 'Comedia'),
('Amante del Drama', 'Ve 3 películas de Drama', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Amante del Drama'), 'genero', 'Drama'),
('Aventura sin límites', 'Ve 3 películas de Aventura', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Aventura sin límites'), 'genero', 'Aventura'),
('Seguidor de Denis Villeneuve', 'Ve 2 películas dirigidas por Denis Villeneuve', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Seguidor de Denis Villeneuve'), 'director', 'Denis Villeneuve'),
('Seguidor de Christopher Nolan', 'Ve 2 películas dirigidas por Christopher Nolan', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Seguidor de Christopher Nolan'), 'director', 'Christopher Nolan'),
('Seguidor de Greta Gerwig', 'Ve 2 películas dirigidas por Greta Gerwig', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Seguidor de Greta Gerwig'), 'director', 'Greta Gerwig'),
('Fan de Timothée Chalamet', 'Ve 2 películas con Timothée Chalamet', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Timothée Chalamet'), 'actor', 'Timothée Chalamet'),
('Fan de Zendaya', 'Ve 2 películas con Zendaya', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Zendaya'), 'actor', 'Zendaya'),
('Fan de Margot Robbie', 'Ve 2 películas con Margot Robbie', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Margot Robbie'), 'actor', 'Margot Robbie'),
('Cinéfilo del 2023', 'Ve 3 películas estrenadas en 2023', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Cinéfilo del 2023'), 'anio', '2023'),
('Cinéfilo del 2022', 'Ve 3 películas estrenadas en 2022', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Cinéfilo del 2022'), 'anio', '2022'),
('Cinéfilo del 2021', 'Ve 3 películas estrenadas en 2021', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Cinéfilo del 2021'), 'anio', '2021');

INSERT INTO insignias (nombre, srcImagen) VALUES 
('Maestro del Gore', '/img/insignias/insignia17.png'),
('Cine Musical Clásico', '/img/insignias/insignia18.png'),
('Explorador de Biopics', '/img/insignias/insignia19.png'),
('Fan de la Animación Japonesa', '/img/insignias/insignia20.png'),
('Sello Akira Kurosawa', '/img/insignias/insignia21.png'),
('Mundo Stanley Kubrick', '/img/insignias/insignia22.png'),
('Estilo Yorgos Lanthimos', '/img/insignias/insignia23.png'),
('Visionario Robert Eggers', '/img/insignias/insignia24.png'),
('Sello Woody Allen', '/img/insignias/insignia25.png'),
('Fan de Pedro Pascal', '/img/insignias/insignia26.png'),
('Fan de Ana de Armas', '/img/insignias/insignia27.png'),
('Fan de Austin Butler', '/img/insignias/insignia28.png'),
('Fan de Viola Davis', '/img/insignias/insignia29.png'),
('Fan de Willem Dafoe', '/img/insignias/insignia30.png'),
('Fan de Olivia Colman', '/img/insignias/insignia31.png'),
('Fan de Christian Bale', '/img/insignias/insignia32.png'),
('Fan de Cate Blanchett', '/img/insignias/insignia33.png'),
('Cinéfilo del 2010', '/img/insignias/insignia34.png'),
('Cinéfilo del 2005', '/img/insignias/insignia35.png'),
('Cosecha del 1999', '/img/insignias/insignia36.png'),
('Retro 1980', '/img/insignias/insignia37.png'),
('Psicosis y el 1960', '/img/insignias/insignia38.png'),
('Dorados 50s', '/img/insignias/insignia39.png'),
('Crítico Incansable (200 Val)', '/img/insignias/insignia40.png'),
('Superviviente (300 Pelis)', '/img/insignias/insignia41.png'),
('Cinéfilo de Élite (400 Pelis)', '/img/insignias/insignia42.png'),
('Erudito del Séptimo Arte', '/img/insignias/insignia43.png');


-- 2. INSERCIÓN DE LOGROS ASOCIADOS
INSERT INTO logros (nombreReto, descripcion, objetivo, idInsignia, tipo_requisito, valor_requisito) VALUES 
('Sangre y Vísceras', 'Ve 5 películas de Terror (Gore)', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Maestro del Gore'), 'genero', 'Terror'),
('Ritmo en las Venas', 'Ve 5 películas Musicales', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Cine Musical Clásico'), 'genero', 'Musical'),
('Vidas Reales', 'Ve 3 películas de Drama (Biografía)', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Explorador de Biopics'), 'genero', 'Drama'),
('Otaku del Cine', 'Ve 10 películas de Animación Japonesa', 10, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de la Animación Japonesa'), 'genero', 'Animación'),
('Honor Samurai', 'Ve 3 películas de Akira Kurosawa', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Sello Akira Kurosawa'), 'director', 'Akira Kurosawa'),
('Perfección Kubrick', 'Ve 3 películas de Stanley Kubrick', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Mundo Stanley Kubrick'), 'director', 'Stanley Kubrick'),
('Rareza Lanthimos', 'Ve 2 películas de Yorgos Lanthimos', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Estilo Yorgos Lanthimos'), 'director', 'Yorgos Lanthimos'),
('Folklore Eggers', 'Ve 2 películas de Robert Eggers', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Visionario Robert Eggers'), 'director', 'Robert Eggers'),
('Neurosis Neoyorquina', 'Ve 5 películas de Woody Allen', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Sello Woody Allen'), 'director', 'Woody Allen'),
('El Mandaloriano', 'Ve 3 películas de Pedro Pascal', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Pedro Pascal'), 'actor', 'Pedro Pascal'),
('Chica de Armas', 'Ve 3 películas de Ana de Armas', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Ana de Armas'), 'actor', 'Ana de Armas'),
('Rey del Rock', 'Ve 2 películas de Austin Butler', 2, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Austin Butler'), 'actor', 'Austin Butler'),
('Poder de Viola', 'Ve 3 películas de Viola Davis', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Viola Davis'), 'actor', 'Viola Davis'),
('Duende Dafoe', 'Ve 5 películas de Willem Dafoe', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Willem Dafoe'), 'actor', 'Willem Dafoe'),
('Realeza Colman', 'Ve 3 películas de Olivia Colman', 3, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Olivia Colman'), 'actor', 'Olivia Colman'),
('Caballero Bale', 'Ve 5 películas de Christian Bale', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Christian Bale'), 'actor', 'Christian Bale'),
('Dama Blanchett', 'Ve 5 películas de Cate Blanchett', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Fan de Cate Blanchett'), 'actor', 'Cate Blanchett'),
('Hito 2010', 'Ve 5 películas del año 2010', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Cinéfilo del 2010'), 'anio', '2010'),
('Hito 2005', 'Ve 5 películas del año 2005', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Cinéfilo del 2005'), 'anio', '2005'),
('La Gran Cosecha', 'Ve 10 películas del año 1999', 10, (SELECT idInsignia FROM insignias WHERE nombre = 'Cosecha del 1999'), 'anio', '1999'),
('Ochenta Puro', 'Ve 5 películas del año 1980', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Retro 1980'), 'anio', '1980'),
('Clásico 1960', 'Ve 5 películas del año 1960', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Psicosis y el 1960'), 'anio', '1960'),
('Dorados 50s', 'Ve 5 películas del año 1955', 5, (SELECT idInsignia FROM insignias WHERE nombre = 'Dorados 50s'), 'anio', '1955'),
('Crítico Incansable', 'Haz un total de 200 valoraciones', 200, (SELECT idInsignia FROM insignias WHERE nombre = 'Crítico Incansable (200 Val)'), 'general', NULL),
('Superviviente', 'Ve un total de 300 películas', 300, (SELECT idInsignia FROM insignias WHERE nombre = 'Superviviente (300 Pelis)'), 'general', NULL),
('Cinéfilo de Élite', 'Ve un total de 400 películas', 400, (SELECT idInsignia FROM insignias WHERE nombre = 'Cinéfilo de Élite (400 Pelis)'), 'general', NULL),
('Erudito', 'Consigue un total de 75 insignias', 75, (SELECT idInsignia FROM insignias WHERE nombre = 'Erudito del Séptimo Arte'), 'general', NULL);


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

