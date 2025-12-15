package org.alumno.proyecto.ajedrez.service.impl;

import java.util.Optional;

import org.alumno.proyecto.ajedrez.model.db.PartidaDb;
import org.alumno.proyecto.ajedrez.model.db.UsuarioDb;
import org.alumno.proyecto.ajedrez.repository.PartidaRepository;
import org.alumno.proyecto.ajedrez.repository.UsuarioRepository;  // ← AÑADIR
import org.alumno.proyecto.ajedrez.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PartidaServiceImpl implements PartidaService {

    @Autowired
    private PartidaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;  // ← AÑADIR

    @Override
    public Page<PartidaDb> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<PartidaDb> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public PartidaDb findPartidaDbOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Partida no encontrada con ID: " + id
                ));
    }

    @Override
    public PartidaDb save(PartidaDb partida) {
        // ← AÑADIR: Cargar los usuarios si vienen con ID pero sin datos
        if (partida.getJugadorBlancas() != null && partida.getJugadorBlancas().getId() != null) {
            UsuarioDb blancas = usuarioRepository.findById(partida.getJugadorBlancas().getId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Jugador blancas no encontrado con ID: " + partida.getJugadorBlancas().getId()
                ));
            partida.setJugadorBlancas(blancas);
        }

        if (partida.getJugadorNegras() != null && partida.getJugadorNegras().getId() != null) {
            UsuarioDb negras = usuarioRepository.findById(partida.getJugadorNegras().getId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Jugador negras no encontrado con ID: " + partida.getJugadorNegras().getId()
                ));
            partida.setJugadorNegras(negras);
        }

        if (partida.getGanador() != null && partida.getGanador().getId() != null) {
            UsuarioDb ganador = usuarioRepository.findById(partida.getGanador().getId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Ganador no encontrado con ID: " + partida.getGanador().getId()
                ));
            partida.setGanador(ganador);
        }

        return repository.save(partida);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}