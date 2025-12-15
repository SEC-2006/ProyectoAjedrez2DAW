package org.alumno.proyecto.ajedrez.controller;

import org.alumno.proyecto.ajedrez.model.db.PartidaDb;
import org.alumno.proyecto.ajedrez.model.db.UsuarioDb;
import org.alumno.proyecto.ajedrez.model.dto.PartidaInfo;
import org.alumno.proyecto.ajedrez.model.dto.PartidaList;
import org.alumno.proyecto.ajedrez.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.alumno.proyecto.ajedrez.service.mapper.PartidaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private PartidaMapper partidaMapper;

    @GetMapping
    public List<PartidaList> listarPartidas(Pageable pageable) {
        Page<PartidaDb> partidas = partidaService.findAll(pageable);
        return partidaMapper.partidasDbToPartidasList(partidas.getContent());
    }

    @GetMapping("/{id}")
    public PartidaInfo obtenerPartida(@PathVariable Long id) {
        PartidaDb partida = partidaService.findById(id).orElse(null);
        if (partida == null) {
            return null;
        }
        return partidaMapper.partidaDbToPartidaInfo(partida);
    }

    @PostMapping
    public PartidaInfo crearPartida(@RequestBody PartidaInfo partidaInfo) {
        PartidaDb partidaDb = partidaMapper.partidaInfoToPartidaDb(partidaInfo);
        
        if (partidaInfo.getIdJugadorBlancas() != null) {
            UsuarioDb blancas = new UsuarioDb();
            blancas.setId(partidaInfo.getIdJugadorBlancas());
            partidaDb.setJugadorBlancas(blancas);
        }
        
        if (partidaInfo.getIdJugadorNegras() != null) {
            UsuarioDb negras = new UsuarioDb();
            negras.setId(partidaInfo.getIdJugadorNegras());
            partidaDb.setJugadorNegras(negras);
        }
        
        if (partidaInfo.getIdGanador() != null) {
            UsuarioDb ganador = new UsuarioDb();
            ganador.setId(partidaInfo.getIdGanador());
            partidaDb.setGanador(ganador);
        }
        
        PartidaDb guardada = partidaService.save(partidaDb);
        return partidaMapper.partidaDbToPartidaInfo(guardada);
    }

    // ← NUEVO MÉTODO PUT
    @PutMapping("/{id}")
    public PartidaInfo actualizarPartida(@PathVariable Long id, @RequestBody PartidaInfo partidaInfo) {
        // Verificar que la partida existe
        PartidaDb partidaExistente = partidaService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Partida no encontrada con ID: " + id
            ));
        
        // Mapear los datos nuevos
        PartidaDb partidaDb = partidaMapper.partidaInfoToPartidaDb(partidaInfo);
        partidaDb.setId(id); // Mantener el ID original
        
        // Asignar los jugadores con IDs
        if (partidaInfo.getIdJugadorBlancas() != null) {
            UsuarioDb blancas = new UsuarioDb();
            blancas.setId(partidaInfo.getIdJugadorBlancas());
            partidaDb.setJugadorBlancas(blancas);
        }
        
        if (partidaInfo.getIdJugadorNegras() != null) {
            UsuarioDb negras = new UsuarioDb();
            negras.setId(partidaInfo.getIdJugadorNegras());
            partidaDb.setJugadorNegras(negras);
        }
        
        if (partidaInfo.getIdGanador() != null) {
            UsuarioDb ganador = new UsuarioDb();
            ganador.setId(partidaInfo.getIdGanador());
            partidaDb.setGanador(ganador);
        }
        
        // Guardar y retornar
        PartidaDb actualizada = partidaService.save(partidaDb);
        return partidaMapper.partidaDbToPartidaInfo(actualizada);
    }

    // ← OPCIONAL: Método DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPartida(@PathVariable Long id) {
        if (!partidaService.findById(id).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Partida no encontrada con ID: " + id
            );
        }
        partidaService.delete(id);
    }
}