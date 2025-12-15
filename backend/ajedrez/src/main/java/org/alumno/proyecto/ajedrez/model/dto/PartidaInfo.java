package org.alumno.proyecto.ajedrez.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidaInfo {
    private Long id;
    private Long idJugadorBlancas;  // ← CAMBIO: Long en lugar de String
    private Long idJugadorNegras;   // ← CAMBIO: Long en lugar de String
    private Long idGanador;         // ← CAMBIO: Long en lugar de String
    private LocalDateTime fechaInicio;

    private List<MovimientoInfo> movimientos;
}