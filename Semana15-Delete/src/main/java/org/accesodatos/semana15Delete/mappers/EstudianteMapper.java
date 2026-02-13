package org.accesodatos.semana15Delete.mappers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana15Delete.dtos.request.create.EstudianteCreateDTO;
import org.accesodatos.semana15Delete.dtos.request.update.EstudianteUpdateDTO;
import org.accesodatos.semana15Delete.dtos.response.AsignaturaCalificacionDTO;
import org.accesodatos.semana15Delete.dtos.response.EstudianteDTO;
import org.accesodatos.semana15Delete.models.Estudiante;
import org.accesodatos.semana15Delete.models.Mascota;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class EstudianteMapper {

    private final MascotaMapper mascotaMapper;

    // --- 1. De Entidad a DTO (GET) ---
    public EstudianteDTO toDto(Estudiante estudiante) {
        if (estudiante == null) return null;

        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(estudiante.getIdEstudiante());
        dto.setNombre(estudiante.getNombre() + " " + estudiante.getApellido());
        dto.setAnyoCurso(estudiante.getAnyoCurso());
        dto.setFechaNacimiento(estudiante.getFechaNacimiento());

        if (estudiante.getCasa() != null) {
            dto.setCasa(estudiante.getCasa().getNombre());
        }

        dto.setMascota(mascotaMapper.toDto(estudiante.getMascota()));

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

    // --- 2. De DTO a Entidad (POST) ---
    public Estudiante toEntity(EstudianteCreateDTO dto) {
        if (dto == null) return null;

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setAnyoCurso(dto.getAnyoCurso());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());

        if (dto.getMascota() != null) {
            Mascota mascota = mascotaMapper.toEntity(dto.getMascota());
            mascota.setEstudiante(estudiante);
            estudiante.setMascota(mascota);
        }
        return estudiante;
    }

    // --- 3. PUT  ---
    public void updateEstudianteFromDto(EstudianteUpdateDTO dto, Estudiante estudiante) {
        if (dto == null || estudiante == null) return;

        estudiante.setAnyoCurso(dto.getAnyoCurso());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());

        if (dto.getMascota() != null) {
            if (estudiante.getMascota() != null) {
                // CASO A: Ya tiene mascota
                mascotaMapper.updateMascotaFromDto(dto.getMascota(), estudiante.getMascota());
            } else {
                // CASO B: No tenía mascota
                Mascota nuevaMascota = mascotaMapper.toEntity(dto.getMascota());
                nuevaMascota.setEstudiante(estudiante);
                estudiante.setMascota(nuevaMascota);
            }
        } else {
            // CASO C: Viene null
            if (estudiante.getMascota() != null) {
                estudiante.setMascota(null);
            }
        }
    }
}