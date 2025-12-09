CREATE DATABASE ajedrez;
USE ajedrez;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE partidas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador_blancas INT NOT NULL,
    id_jugador_negras INT NOT NULL,
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ganador INT NULL,
    FOREIGN KEY (id_jugador_blancas) REFERENCES usuarios(id),
    FOREIGN KEY (id_jugador_negras) REFERENCES usuarios(id),
    FOREIGN KEY (ganador) REFERENCES usuarios(id)
);

CREATE TABLE movimientos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_partida INT NOT NULL,
    numero_movimiento INT NOT NULL,
    movimiento_notacion VARCHAR(20) NOT NULL,
    tiempo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_partida) REFERENCES partidas(id)
);
