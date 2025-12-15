package org.alumno.proyecto.ajedrez.service;

import org.alumno.proyecto.ajedrez.model.db.MovimientoDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovimientoService {

    MovimientoDb findById(Long id);

    MovimientoDb save(MovimientoDb movimiento);

    void delete(Long id);

    Page<MovimientoDb> findMovimientos(Long idPartida, String movimiento, Pageable pageable);
}
