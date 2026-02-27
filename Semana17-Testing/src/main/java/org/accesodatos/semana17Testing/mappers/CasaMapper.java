package org.accesodatos.semana17Testing.mappers;

import org.accesodatos.semana17Testing.dtos.response.CasaDTO;
import org.accesodatos.semana17Testing.models.Casa;
import org.springframework.stereotype.Component;

/**
 * CAPA DE MAPEO: CASA MAPPER
 * --------------------------
 * Esta clase es un componente encargado de transformar la
 * entidad compleja 'Casa' en un 'CasaDTO'. Es un ejemplo de cómo se gestionan
 * las relaciones de objetos anidados y colecciones al enviar datos a la API.
 */

@Component // Permite que Spring gestione esta clase y la inyecte donde sea necesaria.
public class CasaMapper {

    // COMPOSICIÓN DE MAPPERS:
    // Como una Casa tiene un Jefe (que es un Profesor), este mapper
    // reutiliza el 'ProfesorMapper' para no repetir lógica de conversión.
    private final ProfesorMapper profesorMapper;

    public CasaMapper(ProfesorMapper profesorMapper) {
        this.profesorMapper = profesorMapper;
    }

    /**
     * METODO toDto: Transforma la entidad en el objeto de respuesta.
     */
    public CasaDTO toDto(Casa casa) {
        if (casa == null) {
            return null; // Evita errores de puntero nulo (NullPointerException).
        }

        CasaDTO dto = new CasaDTO();

        // Mapeo de atributos básicos
        dto.setId(casa.getIdCasa());
        dto.setNombre(casa.getNombre());
        dto.setFundador(casa.getFundador());
        dto.setFantasma(casa.getFantasma());

        /**
         * MAPEO DE OBJETO ANIDADO (Relación 1:1)
         * --------------------------------------
         * Aquí delegamos en 'profesorMapper'. En lugar de copiar
         * campo a campo al profesor, llamamos a su propio mapper para obtener
         * una representación estandarizada del jefe de la casa.
         */
        dto.setJefe(profesorMapper.toDto(casa.getJefeCasa()));

        /**
         * MAPEO DE COLECCIONES (Relación 1:N)
         * -----------------------------------
         * Una casa tiene muchos estudiantes (Set<Estudiante>).
         * Para el DTO, usamos Java Streams para "aplanar" la lista:
         * 1. Tomamos los estudiantes.
         * 2. Mapeamos cada objeto Estudiante a un simple String (Nombre + Apellido).
         * 3. Lo convertimos en una Lista de Strings.
         * RESULTADO: El JSON no pesará megabytes con objetos anidados, solo nombres.
         */
        dto.setEstudiantes(
                casa.getEstudiantes() != null
                        ? casa.getEstudiantes().stream()
                        .map(t -> t.getNombre() + " " + t.getApellido())
                        .toList()
                        : null);

        return dto;
    }
}