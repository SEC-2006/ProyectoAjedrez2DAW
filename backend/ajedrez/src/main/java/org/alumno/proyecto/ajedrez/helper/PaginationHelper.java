package org.alumno.proyecto.ajedrez.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest; // Implementación concreta de Pageable
import org.springframework.data.domain.Pageable; // Interfaz que representa paginación
import org.springframework.data.domain.Sort; // Para ordenación
import org.springframework.data.domain.Sort.Direction; // Dirección de orden (ASC/DESC)
import org.springframework.data.domain.Sort.Order; // Representa un criterio de ordenación

// Clase helper para crear objetos Pageable con paginación y ordenación
public class PaginationHelper {

    private PaginationHelper() { // Constructor privado para evitar instanciación
    }

    /**
     * Crea un objeto Pageable a partir de los parámetros dados.
     *
     * @param page Número de página (empieza en 0)
     * @param size Tamaño de la página
     * @param sort Array de criterios de ordenación (ej. "campo,asc")
     * @return Objeto Pageable con la paginación y ordenación configurada
     */
    public static Pageable createPageable(int page, int size, String[] sort) {

        List<Order> criteriosOrdenacion = new ArrayList<Order>(); // Lista de criterios de orden

        if (sort[0].contains(",")) { // Si el primer elemento contiene "," se asume formato "campo,direccion"
            for (String criterioOrdenacion : sort) {
                String[] orden = criterioOrdenacion.split(","); // Separa campo y dirección
                if (orden.length > 1) {
                    criteriosOrdenacion.add(new Order(Direction.fromString(orden[1]), orden[0])); // Crea criterio con dirección
                } else {
                    criteriosOrdenacion.add(new Order(Direction.fromString("asc"), orden[0])); // Por defecto ascendente
                }
            }
        } else { // Caso en que sort no tiene "," en el primer elemento
            criteriosOrdenacion.add(new Order(Direction.fromString(sort[1]), sort[0])); // Ordena por el segundo elemento como dirección
        }

        Sort sorts = Sort.by(criteriosOrdenacion); // Crea un objeto Sort a partir de la lista de criterios

        return PageRequest.of(page, size, sorts); // Retorna el objeto Pageable con página, tamaño y orden
    }
}