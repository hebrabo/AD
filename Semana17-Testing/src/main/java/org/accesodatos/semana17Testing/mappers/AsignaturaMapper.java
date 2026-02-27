package org.accesodatos.semana17Testing.mappers;

import org.accesodatos.semana17Testing.dtos.response.AsignaturaDTO;
import org.accesodatos.semana17Testing.models.Asignatura;
import org.springframework.stereotype.Component;

/**
 * CAPA DE MAPEO (MAPPERS)
 * -----------------------
 * El Mapper es un "traductor". Su única misión es convertir
 * objetos complejos de la Base de Datos (Entidades) en objetos simples para
 * la API (DTOs). Esto garantiza que no enviemos información sensible o pesada al cliente.
 */

@Component // Indica a Spring que esta clase es un bean de utilidad que puede ser inyectado en los Servicios.
public class AsignaturaMapper {

    /**
     * METODO toDto: Convierte una Entidad Asignatura en un AsignaturaDTO.
     * Aquí ocurre la transformación de los datos.
     */
    public AsignaturaDTO toDto(Asignatura asignatura) {
        if (asignatura == null) {
            return null; // Control de errores: si no hay asignatura, devolvemos null para evitar NullPointerException.
        }

        AsignaturaDTO dto = new AsignaturaDTO();

        // Mapeo simple: Atributos directos
        dto.setId(asignatura.getId());
        dto.setNombre(asignatura.getNombre());
        dto.setAula(asignatura.getAula());
        dto.setObligatoria(asignatura.getObligatoria());

        /**
         * MAPEO DE RELACIONES
         * -------------------------------------------
         * En la base de datos, 'profesor' es un objeto completo con ID,
         * fecha de inicio, etc. Sin embargo, en el DTO solo queremos mostrar el nombre legible.
         * El mapper "aplana" la relación convirtiendo un objeto complejo en un simple String.
         */
        if (asignatura.getProfesor() != null) {
            // Concatenamos nombre y apellido para que el usuario del Swagger vea: "Severus Snape"
            dto.setProfesor(
                    asignatura.getProfesor().getNombre() + " " + asignatura.getProfesor().getApellido()
            );
        }

        return dto;
    }
}