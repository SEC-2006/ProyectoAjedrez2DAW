package org.alumno.proyecto.ajedrez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.alumno.proyecto.ajedrez.model.db.PartidaDb;

public interface PartidaRepository extends JpaRepository<PartidaDb, Long> {}
