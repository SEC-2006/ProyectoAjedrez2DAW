package org.alumno.proyecto.ajedrez.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.alumno.proyecto.ajedrez.model.db.UsuarioDb;

public interface UsuarioRepository extends JpaRepository<UsuarioDb, Long> {

    UsuarioDb findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}