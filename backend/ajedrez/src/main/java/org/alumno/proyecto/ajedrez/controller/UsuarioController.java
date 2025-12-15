package org.alumno.proyecto.ajedrez.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.alumno.proyecto.ajedrez.model.dto.LoginUsuario;
import org.alumno.proyecto.ajedrez.model.dto.RegistroUsuario;
import org.alumno.proyecto.ajedrez.model.dto.UsuarioInfo;
import org.alumno.proyecto.ajedrez.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(@RequestBody LoginUsuario login) {
        return usuarioService.comprobarLogin(login)
                ? "Login correcto"
                : "Credenciales incorrectas";
    }

    @PostMapping("/register")
    public UsuarioInfo register(@RequestBody RegistroUsuario newUser) {
        return usuarioService.registrar(newUser);
    }

    @GetMapping("/{nickname}")
    public UsuarioInfo getByNickname(@PathVariable String nickname) {
        return usuarioService.getByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @GetMapping("/exists/nickname/{nickname}")
    public boolean existsByNickname(@PathVariable String nickname) {
        return usuarioService.existsByNickname(nickname);
    }

    @GetMapping("/exists/email/{email}")
    public boolean existsByEmail(@PathVariable String email) {
        return usuarioService.existsByEmail(email);
    }

}
