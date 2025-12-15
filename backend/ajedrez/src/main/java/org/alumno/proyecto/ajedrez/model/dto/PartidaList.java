package org.alumno.proyecto.ajedrez.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidaList {

    private Long id;                // ID de la partida
    private String jugadorBlancas;  // Nombre del jugador de blancas
    private String jugadorNegras;   // Nombre del jugador de negras
    private String ganador;         // Nombre del ganador (puede ser null)
    private String fechaInicio;     // Fecha de inicio de la partida

}
