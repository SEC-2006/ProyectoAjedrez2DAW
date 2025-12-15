package org.alumno.proyecto.ajedrez.service;

import java.util.Optional;

import org.alumno.proyecto.ajedrez.model.dto.LoginUsuario;
import org.alumno.proyecto.ajedrez.model.dto.RegistroUsuario;
import org.alumno.proyecto.ajedrez.model.dto.UsuarioInfo;

public interface UsuarioService {

    Optional<UsuarioInfo> getByNickname(String nickname);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean comprobarLogin(LoginUsuario loginUsuario);

    UsuarioInfo registrar(RegistroUsuario newUser);
}
