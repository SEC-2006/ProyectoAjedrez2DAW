package org.alumno.proyecto.ajedrez.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioInfo {
    private Long id;
    private String username;
    private String email;
    private String fechaRegistro;
}
