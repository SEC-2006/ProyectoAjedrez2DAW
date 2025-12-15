package org.alumno.proyecto.ajedrez.service.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.alumno.proyecto.ajedrez.model.db.PartidaDb;
import org.alumno.proyecto.ajedrez.model.dto.PartidaInfo;
import org.alumno.proyecto.ajedrez.model.dto.PartidaList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartidaMapper {

    PartidaMapper INSTANCE = Mappers.getMapper(PartidaMapper.class);

    // ===========================
    //     DB → PartidaInfo
    // ===========================

    @Mapping(source = "jugadorBlancas.id", target = "idJugadorBlancas")  // ← CAMBIO
    @Mapping(source = "jugadorNegras.id", target = "idJugadorNegras")    // ← CAMBIO
    @Mapping(source = "ganador.id", target = "idGanador")                // ← CAMBIO
    @Mapping(target = "movimientos", ignore = true)
    PartidaInfo partidaDbToPartidaInfo(PartidaDb db);

    // ===========================
    //     DB → PartidaList
    // ===========================

    @Mapping(source = "jugadorBlancas.username", target = "jugadorBlancas")
    @Mapping(source = "jugadorNegras.username", target = "jugadorNegras")
    @Mapping(source = "ganador.username", target = "ganador")
    @Mapping(source = "fechaInicio", target = "fechaInicio", qualifiedByName = "fechaToString")
    PartidaList partidaDbToPartidaList(PartidaDb db);

    List<PartidaList> partidasDbToPartidasList(List<PartidaDb> lista);

    // ===========================
    //     PartidaInfo → DB
    // ===========================
    // Se ignoran porque se asignarán en el Servicio
    @Mapping(target = "jugadorBlancas", ignore = true)
    @Mapping(target = "jugadorNegras", ignore = true)
    @Mapping(target = "ganador", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    PartidaDb partidaInfoToPartidaDb(PartidaInfo info);

    // ===========================
    //     PartidaList → DB
    // ===========================

    @Mapping(target = "jugadorBlancas", ignore = true)
    @Mapping(target = "jugadorNegras", ignore = true)
    @Mapping(target = "ganador", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    @Mapping(source = "fechaInicio", target = "fechaInicio", qualifiedByName = "stringToFecha")
    PartidaDb partidaListToPartidaDb(PartidaList list);

    // ===========================
    //     Métodos auxiliares
    // ===========================

    @Named("fechaToString")
    public static String fechaToString(LocalDateTime fecha) {
        return fecha != null ? fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    @Named("stringToFecha")
    public static LocalDateTime stringToFecha(String fecha) {
        return (fecha != null && !fecha.isBlank())
                ? LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : null;
    }
}