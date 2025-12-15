package org.alumno.proyecto.ajedrez.repository;

import org.alumno.proyecto.ajedrez.model.db.MovimientoDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<MovimientoDb, Long> {

    // Filtrado por idPartida
    Page<MovimientoDb> findByPartidaId(Long idPartida, Pageable pageable);

    // Filtrado por idPartida y notación del movimiento
    Page<MovimientoDb> findByPartidaIdAndMovimientoNotacionContainingIgnoreCase(Long idPartida, String movimiento, Pageable pageable);
}

