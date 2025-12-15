package org.alumno.proyecto.ajedrez.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import org.alumno.proyecto.ajedrez.model.db.UsuarioDb;
import org.alumno.proyecto.ajedrez.model.dto.UsuarioInfo;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioInfo usuarioDbToInfo(UsuarioDb usuarioDb);
}
