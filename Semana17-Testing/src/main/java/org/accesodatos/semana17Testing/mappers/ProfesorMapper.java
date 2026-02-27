package org.accesodatos.semana17Testing.mappers;

import org.accesodatos.semana17Testing.dtos.response.ProfesorDTO;
import org.accesodatos.semana17Testing.models.Profesor;
import org.springframework.stereotype.Component;

/**
 * MAPPER DE PROFESORES
 * -------------------
 * Esta clase implementa el patrón Mapper para la entidad Profesor.
 * Su función es transformar la entidad JPA (con toda su carga de base de datos)
 * en un objeto DTO limpio y optimizado para la red.
 */

@Component // Indica a Spring que es un componente reutilizable e inyectable.
public class ProfesorMapper {

    /**
     * METODO toDto: Transforma la Entidad en DTO de respuesta.
     */
    public ProfesorDTO toDto(Profesor profesor) {
        if (profesor == null) {
            return null; // Control de seguridad para evitar errores al consultar profesores inexistentes.
        }

        ProfesorDTO dto = new ProfesorDTO();

        // MAPEADO BÁSICO:
        dto.setId(profesor.getId());

        // CONCATENACIÓN (Abstracción):
        // En lugar de obligar al Frontend a unir el nombre y apellido,
        // el Mapper entrega el nombre completo ya formateado.
        dto.setNombre(profesor.getNombre() + " " + profesor.getApellido());

        /**
         * GESTIÓN DE LA RELACIÓN 1:1 (ASIGNATURA)
         * --------------------------------------
         * Un profesor imparte una asignatura. En la BD, esto es una
         * relación compleja. En el DTO, aplicamos "aplanamiento" (flattening) para que
         * solo se vea el nombre de la materia (ej: "Defensa Contra las Artes Oscuras").
         */
        if (profesor.getAsignatura() != null) {
            dto.setAsignatura(profesor.getAsignatura().getNombre());
        }

        dto.setFechaInicio(profesor.getFechaInicio());

        return dto;
    }
}