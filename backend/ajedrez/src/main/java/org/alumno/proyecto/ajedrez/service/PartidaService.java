package org.alumno.proyecto.ajedrez.service;

import org.alumno.proyecto.ajedrez.model.db.PartidaDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface PartidaService {
    Page<PartidaDb> findAll(Pageable pageable);
    
    // Método existente
    Optional<PartidaDb> findById(Long id);
    
    // ⭐ NUEVO MÉTODO ⭐ para obtener la entidad directamente
    PartidaDb findPartidaDbOrThrow(Long id); 
    
    PartidaDb save(PartidaDb partida);
    void delete(Long id);
}