package org.alumno.proyecto.ajedrez.service.impl;

import org.alumno.proyecto.ajedrez.model.db.MovimientoDb;
import org.alumno.proyecto.ajedrez.model.db.PartidaDb;
import org.alumno.proyecto.ajedrez.repository.MovimientoRepository;
import org.alumno.proyecto.ajedrez.repository.PartidaRepository;
import org.alumno.proyecto.ajedrez.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository repository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Override
    public MovimientoDb findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public MovimientoDb save(MovimientoDb movimiento) {
        // Cargar la partida completa si viene con ID pero sin datos
        if (movimiento.getPartida() != null && movimiento.getPartida().getId() != null) {
            PartidaDb partida = partidaRepository.findById(movimiento.getPartida().getId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Partida no encontrada con ID: " + movimiento.getPartida().getId()
                ));
            movimiento.setPartida(partida);
        }
        
        return repository.save(movimiento);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Movimiento no encontrado con ID: " + id
            );
        }
        repository.deleteById(id);
    }

  @Override
public Page<MovimientoDb> findMovimientos(
        Long idPartida,
        String movimiento,
        Pageable pageable
) {
    // Sin filtros → todos
    if (idPartida == null && (movimiento == null || movimiento.isBlank())) {
        return repository.findAll(pageable);
    }

    // Solo partida
    if (idPartida != null && (movimiento == null || movimiento.isBlank())) {
        return repository.findByPartidaId(idPartida, pageable);
    }

    // Ambos filtros
    return repository.findByPartidaIdAndMovimientoNotacionContainingIgnoreCase(
            idPartida, movimiento, pageable);
}
}