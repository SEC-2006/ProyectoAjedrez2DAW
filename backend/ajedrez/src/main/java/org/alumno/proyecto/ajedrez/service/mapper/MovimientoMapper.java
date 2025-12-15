package org.alumno.proyecto.ajedrez.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.alumno.proyecto.ajedrez.model.db.MovimientoDb;
import org.alumno.proyecto.ajedrez.model.dto.MovimientoInfo;
import org.alumno.proyecto.ajedrez.model.dto.MovimientoList;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);


    // ============ DB → DTO =============

    @Mapping(source = "partida.id", target = "idPartida")
    MovimientoInfo movimientoDbToMovimientoInfo(MovimientoDb movimientoDb);

    @Mapping(source = "partida.id", target = "idPartida")
    MovimientoList movimientoDbToMovimientoList(MovimientoDb movimientoDb);

    List<MovimientoList> movimientosDbToMovimientosList(List<MovimientoDb> movimientosDb);



    // ============ DTO → DB =============

    // En DTO tenemos idPartida = Long; en DB tenemos partida = PartidaDb
    // Por eso lo ignoramos (se asigna en el servicio)
    @Mapping(target = "partida", ignore = true)
    MovimientoDb movimientoInfoToMovimientoDb(MovimientoInfo info);
}
