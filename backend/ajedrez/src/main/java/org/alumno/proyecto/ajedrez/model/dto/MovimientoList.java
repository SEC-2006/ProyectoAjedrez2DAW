package org.alumno.proyecto.ajedrez.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovimientoList {
    private Long id;
    private Long idPartida;
    private Integer numeroMovimiento;
    private String movimientoNotacion;
    private String tiempo;
}
