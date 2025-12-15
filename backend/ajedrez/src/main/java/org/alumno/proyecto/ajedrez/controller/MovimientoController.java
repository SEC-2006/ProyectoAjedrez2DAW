package org.alumno.proyecto.ajedrez.controller;

import org.alumno.proyecto.ajedrez.helper.PaginationHelper;
import org.alumno.proyecto.ajedrez.model.db.MovimientoDb;
import org.alumno.proyecto.ajedrez.model.db.PartidaDb;
import org.alumno.proyecto.ajedrez.model.dto.MovimientoInfo;
import org.alumno.proyecto.ajedrez.service.MovimientoService;
import org.alumno.proyecto.ajedrez.service.mapper.MovimientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;
    
    /**
     * Listar movimientos de una partida con paginación y filtro opcional
     * GET /api/movimientos?idPartida=1&page=0&size=5
     * GET /api/movimientos?idPartida=1&movimiento=e&page=0&size=5
     */
  @GetMapping
public ResponseEntity<?> getMovimientos(
        @RequestParam(required = false) Long idPartida,
        @RequestParam(required = false) String movimiento,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "numeroMovimiento,asc") String[] sort
)
 {
        // Creamos Pageable usando el helper
        Pageable pageable = PaginationHelper.createPageable(page, size, sort);

        // Obtenemos página de movimientos filtrada
        Page<MovimientoDb> pageResult = movimientoService.findMovimientos(idPartida, movimiento, pageable);

        // Mapeamos contenido a DTO
        List<?> content = MovimientoMapper.INSTANCE.movimientosDbToMovimientosList(pageResult.getContent());

        // Devolvemos un objeto con la información de paginación
        return ResponseEntity.ok(new PageResponse<>(pageResult.getNumber(),
                                                    pageResult.getSize(),
                                                    pageResult.getTotalElements(),
                                                    pageResult.getTotalPages(),
                                                    content));
    }

    /**
     * Obtener un movimiento específico por ID
     * GET /api/movimientos/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInfo> getMovimientoById(@PathVariable Long id) {
        MovimientoDb movimiento = movimientoService.findById(id);
        
        if (movimiento == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Movimiento no encontrado con ID: " + id
            );
        }
        
        MovimientoInfo movimientoInfo = MovimientoMapper.INSTANCE.movimientoDbToMovimientoInfo(movimiento);
        return ResponseEntity.ok(movimientoInfo);
    }

    /**
     * Crear un nuevo movimiento
     * POST /api/movimientos
     */
    @PostMapping
    public ResponseEntity<MovimientoInfo> crearMovimiento(@RequestBody MovimientoInfo movimientoInfo) {
        // Mapear DTO a entidad
        MovimientoDb movimientoDb = MovimientoMapper.INSTANCE.movimientoInfoToMovimientoDb(movimientoInfo);
        
        // Asignar la partida usando el ID
        if (movimientoInfo.getIdPartida() != null) {
            PartidaDb partida = new PartidaDb();
            partida.setId(movimientoInfo.getIdPartida());
            movimientoDb.setPartida(partida);
        } else {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El campo idPartida es obligatorio"
            );
        }
        
        // Guardar
        MovimientoDb guardado = movimientoService.save(movimientoDb);
        
        // Convertir a DTO y retornar
        MovimientoInfo resultado = MovimientoMapper.INSTANCE.movimientoDbToMovimientoInfo(guardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    /**
     * Actualizar un movimiento existente
     * PUT /api/movimientos/4
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovimientoInfo> actualizarMovimiento(
            @PathVariable Long id,
            @RequestBody MovimientoInfo movimientoInfo) {
        
        // Verificar que existe
        MovimientoDb existente = movimientoService.findById(id);
        if (existente == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Movimiento no encontrado con ID: " + id
            );
        }
        
        // Mapear DTO a entidad
        MovimientoDb movimientoDb = MovimientoMapper.INSTANCE.movimientoInfoToMovimientoDb(movimientoInfo);
        movimientoDb.setId(id); // Mantener el ID original
        
        // Asignar la partida usando el ID
        if (movimientoInfo.getIdPartida() != null) {
            PartidaDb partida = new PartidaDb();
            partida.setId(movimientoInfo.getIdPartida());
            movimientoDb.setPartida(partida);
        } else {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El campo idPartida es obligatorio"
            );
        }
        
        // Actualizar
        MovimientoDb actualizado = movimientoService.save(movimientoDb);
        
        // Convertir a DTO y retornar
        MovimientoInfo resultado = MovimientoMapper.INSTANCE.movimientoDbToMovimientoInfo(actualizado);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Eliminar un movimiento
     * DELETE /api/movimientos/4
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        // Verificar que existe
        MovimientoDb existente = movimientoService.findById(id);
        if (existente == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Movimiento no encontrado con ID: " + id
            );
        }
        
        // Eliminar
        movimientoService.delete(id);
        
        return ResponseEntity.noContent().build();
    }

    // Clase interna para devolver paginación
    public static class PageResponse<T> {
        private int number;
        private int size;
        private long totalElements;
        private int totalPages;
        private List<T> content;

        public PageResponse(int number, int size, long totalElements, int totalPages, List<T> content) {
            this.number = number;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.content = content;
        }

        // Getters y setters
        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
        public long getTotalElements() { return totalElements; }
        public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
        public int getTotalPages() { return totalPages; }
        public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
        public List<T> getContent() { return content; }
        public void setContent(List<T> content) { this.content = content; }
    }
}