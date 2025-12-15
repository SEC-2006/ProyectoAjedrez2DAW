package org.alumno.proyecto.ajedrez.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.alumno.proyecto.ajedrez.model.db.UsuarioDb;
import org.alumno.proyecto.ajedrez.model.dto.LoginUsuario;
import org.alumno.proyecto.ajedrez.model.dto.RegistroUsuario;
import org.alumno.proyecto.ajedrez.model.dto.UsuarioInfo;
import org.alumno.proyecto.ajedrez.repository.UsuarioRepository;
import org.alumno.proyecto.ajedrez.service.UsuarioService;
import org.alumno.proyecto.ajedrez.service.mapper.UsuarioMapper;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<UsuarioInfo> getByNickname(String nickname) {
        UsuarioDb u = repo.findByUsername(nickname);
        if (u == null) return Optional.empty();
        return Optional.of(UsuarioMapper.INSTANCE.usuarioDbToInfo(u));
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return repo.existsByUsername(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public boolean comprobarLogin(LoginUsuario loginUsuario) {
        UsuarioDb u = repo.findByUsername(loginUsuario.getNickname());
        if (u == null) return false;
        // Compara la contraseña directamente, sin hash
        return u.getPassword().equals(loginUsuario.getPassword());
    }

    @Override
    public UsuarioInfo registrar(RegistroUsuario newUser) {
        UsuarioDb u = new UsuarioDb();
        u.setUsername(newUser.getNickname());
        u.setEmail(newUser.getEmail());
        // Guarda la contraseña tal cual (sin hash)
        u.setPassword(newUser.getPassword());
        u.setFechaRegistro(LocalDateTime.now().toString());

        repo.save(u);
        return UsuarioMapper.INSTANCE.usuarioDbToInfo(u);
    }
}
