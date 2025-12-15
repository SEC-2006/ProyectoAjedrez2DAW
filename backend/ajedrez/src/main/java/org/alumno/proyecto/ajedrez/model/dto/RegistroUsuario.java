package org.alumno.proyecto.ajedrez.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroUsuario {

    @Size(min = 5, message = "El nickname debe tener al menos 5 caracteres")
    private String nickname;

    @Email(message = "El email no es válido")
    private String email;

    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "El password debe tener mínimo 8 caracteres, mayúscula, minúscula, número y caracter especial"
    )
    private String password;
}
