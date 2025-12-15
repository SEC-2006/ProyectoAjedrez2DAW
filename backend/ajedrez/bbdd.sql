------------------------------------------------------
-- ELIMINAR ESQUEMA SI EXISTE
------------------------------------------------------
DROP SCHEMA IF EXISTS ajedrez CASCADE;

-- CREAR ESQUEMA
CREATE SCHEMA ajedrez;

-- USAR EL ESQUEMA POR DEFECTO
SET search_path TO ajedrez;

------------------------------------------------------
-- TABLA usuarios
------------------------------------------------------
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

------------------------------------------------------
-- TABLA partidas
------------------------------------------------------
CREATE TABLE partidas (
    id SERIAL PRIMARY KEY,
    id_jugador_blancas INT NOT NULL,
    id_jugador_negras INT NOT NULL,
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ganador INT NULL,
    FOREIGN KEY (id_jugador_blancas) REFERENCES usuarios(id),
    FOREIGN KEY (id_jugador_negras) REFERENCES usuarios(id),
    FOREIGN KEY (ganador) REFERENCES usuarios(id)
);

------------------------------------------------------
-- TABLA movimientos (con FEN)
------------------------------------------------------
CREATE TABLE movimientos (
    id SERIAL PRIMARY KEY,
    id_partida INT NOT NULL,
    numero_movimiento INT NOT NULL,
    movimiento_notacion VARCHAR(20) NOT NULL,
    fen_inicial TEXT NOT NULL,  -- Tablero antes del movimiento
    fen_final TEXT NOT NULL,    -- Tablero después del movimiento
    tiempo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_partida) REFERENCES partidas(id)
);

------------------------------------------------------
-- DATOS DE EJEMPLO: usuarios
------------------------------------------------------
INSERT INTO usuarios (username, password, email)
VALUES
('carlos', 'hash1', 'carlos@example.com'),
('maria', 'hash2', 'maria@example.com'),
('juan',  'hash3', 'juan@example.com');

------------------------------------------------------
-- DATOS DE EJEMPLO: partidas
------------------------------------------------------
INSERT INTO partidas (id_jugador_blancas, id_jugador_negras, ganador)
VALUES
(1, 2, 1),
(2, 3, 3),
(1, 3, NULL);

------------------------------------------------------
-- DATOS DE EJEMPLO: movimientos (con FEN)
------------------------------------------------------
INSERT INTO movimientos (id_partida, numero_movimiento, movimiento_notacion, fen_inicial, fen_final)
VALUES
(1, 1, 'e4', 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR', 'rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR'),
(1, 2, 'e5', 'rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR', 'rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR'),
(1, 3, 'Nf3', 'rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR', 'rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R'),
(2, 1, 'd4', 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR', 'rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR'),
(2, 2, 'd5', 'rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR', 'rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR'),
(2, 3, 'c4', 'rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR', 'rnbqkbnr/ppp1pppp/8/3p4/2P5/8/PP2PPPP/RNBQKBNR'),
(3, 1, 'c4', 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR', 'rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR'),
(3, 2, 'Nf6', 'rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR', 'rnbqkb1r/pppppppp/5n2/8/2P5/8/PP1PPPPP/RNBQKBNR');

