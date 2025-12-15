package org.alumno.proyecto.ajedrez.model.dto;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUsuario {

    @Size(min = 5, message = "El nickname debe tener al menos 5 caracteres")
    private String nickname;

    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "El password debe tener mínimo 8 caracteres, mayúscula, minúscula, número y caracter especial"
    )
    private String password;
}