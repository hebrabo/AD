package org.accesodatos.semana17Testing.mappers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana17Testing.dtos.request.create.EstudianteCreateDTO;
import org.accesodatos.semana17Testing.dtos.request.update.EstudianteUpdateDTO;
import org.accesodatos.semana17Testing.dtos.response.AsignaturaCalificacionDTO;
import org.accesodatos.semana17Testing.dtos.response.EstudianteDTO;
import org.accesodatos.semana17Testing.models.Estudiante;
import org.accesodatos.semana17Testing.models.Mascota;
import org.springframework.stereotype.Component;

/**
 * MAPPER DE ESTUDIANTES
 * ----------------------------------------------
 * Esta clase implementa el patrón DTO de forma integral.
 * Maneja tres flujos: Salida (Entity -> DTO), Entrada (DTO -> Entity) y
 * Actualización (Sincronización de objetos).
 */

@Component
@Data
@RequiredArgsConstructor // LOMBOK: Inyecta el MascotaMapper de forma automática y final.
public class EstudianteMapper {

    private final MascotaMapper mascotaMapper;

    /**
     * 1. DE ENTIDAD A DTO (FLUJO DE SALIDA - GET)
     * Preparamos los datos para el cliente (Postman/Swagger).
     * Aplicamos "aplanamiento" (Flattening) para que el JSON sea más sencillo.
     */
    public EstudianteDTO toDto(Estudiante estudiante) {
        if (estudiante == null) return null;

        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(estudiante.getIdEstudiante());
        // CONCATENACIÓN: Transformamos dos campos de la BD en uno solo para la vista.
        dto.setNombre(estudiante.getNombre() + " " + estudiante.getApellido());
        dto.setAnyoCurso(estudiante.getAnyoCurso());
        dto.setFechaNacimiento(estudiante.getFechaNacimiento());

        // RELACIÓN N:1 (CASA): Solo enviamos el nombre de la casa, no el objeto entero.
        if (estudiante.getCasa() != null) {
            dto.setCasa(estudiante.getCasa().getNombre());
        }

        // DELEGACIÓN: Usamos el mapper de mascota para su propia lógica.
        dto.setMascota(mascotaMapper.toDto(estudiante.getMascota()));

        // JAVA STREAMS: Convertimos la colección de calificaciones en una lista de DTOs ligeros.
        if (estudiante.getCalificaciones() != null) {
            dto.setAsignaturas(
                    estudiante.getCalificaciones().stream()
                            .map(calificacionEntity -> {
                                AsignaturaCalificacionDTO asigDto = new AsignaturaCalificacionDTO();
                                asigDto.setAsignatura(calificacionEntity.getAsignatura().getNombre());
                                asigDto.setCalificacion(calificacionEntity.getCalificacion());
                                return asigDto;
                            })
                            .toList()
            );
        }
        return dto;
    }

    /**
     * 2. DE DTO A ENTIDAD (FLUJO DE ENTRADA - POST)
     * Transformamos el JSON de entrada en un objeto que JPA
     * pueda persistir en la base de datos de AWS.
     */
    public Estudiante toEntity(EstudianteCreateDTO dto) {
        if (dto == null) return null;

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setAnyoCurso(dto.getAnyoCurso());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());

        // INTEGRIDAD REFERENCIAL: Al crear la mascota, debemos vincularla
        // bidireccionalmente con el estudiante para que la FK se guarde bien.
        if (dto.getMascota() != null) {
            Mascota mascota = mascotaMapper.toEntity(dto.getMascota());
            mascota.setEstudiante(estudiante); // Seteamos el dueño
            estudiante.setMascota(mascota);    // Seteamos la propiedad
        }
        return estudiante;
    }

    /**
     * 3. ACTUALIZACIÓN (FLUJO DE EDICIÓN - PUT)
     * Este metodo no crea un objeto nuevo, sino que MODIFICA
     * el que ya existe en la memoria de Hibernate (Managed Entity).
     */
    public void updateEstudianteFromDto(EstudianteUpdateDTO dto, Estudiante estudiante) {
        if (dto == null || estudiante == null) return;

        estudiante.setAnyoCurso(dto.getAnyoCurso());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());

        // LÓGICA DE NEGOCIO PARA MASCOTAS:
        if (dto.getMascota() != null) {
            if (estudiante.getMascota() != null) {
                // CASO A: El alumno ya tenía mascota, la actualizamos.
                mascotaMapper.updateMascotaFromDto(dto.getMascota(), estudiante.getMascota());
            } else {
                // CASO B: El alumno no tenía mascota, le asignamos una nueva.
                Mascota nuevaMascota = mascotaMapper.toEntity(dto.getMascota());
                nuevaMascota.setEstudiante(estudiante);
                estudiante.setMascota(nuevaMascota);
            }
        } else {
            // CASO C: Si el DTO viene con mascota null, la eliminamos (Orphan Removal).
            if (estudiante.getMascota() != null) {
                estudiante.setMascota(null);
            }
        }
    }
}