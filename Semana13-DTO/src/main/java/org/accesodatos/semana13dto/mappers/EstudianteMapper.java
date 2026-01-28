package org.accesodatos.semana13dto.mappers;

import org.accesodatos.semana13dto.dtos.AsignaturaCalificacionDTO;
import org.accesodatos.semana13dto.dtos.EstudianteDTO;
import org.accesodatos.semana13dto.models.Estudiante;
import org.springframework.stereotype.Component;

@Component
public class EstudianteMapper {
    private final MascotaMapper mascotaMapper;

    public EstudianteMapper(MascotaMapper mascotaMapper) {
        this.mascotaMapper = mascotaMapper;
    }

    public EstudianteDTO toDto(Estudiante estudiante) {
        if (estudiante == null) {
            return null;
        }
        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(estudiante.getIdEstudiante());
        dto.setNombre(estudiante.getNombre() + " " + estudiante.getApellido());
        dto.setAnyoCurso(estudiante.getAnyoCurso());
        dto.setFechaNacimiento(estudiante.getFechaNacimiento());

        if (estudiante.getCasa() != null) {
            dto.setCasa(estudiante.getCasa().getNombre());
        }

        dto.setMascota(mascotaMapper.toDto(estudiante.getMascota()));

        if (estudiante.getAsignaturas() != null) {
            dto.setAsignaturas(
                    estudiante.getAsignaturas().stream()
                            .map(asignaturaEntity -> {
                                AsignaturaCalificacionDTO asigDto = new AsignaturaCalificacionDTO();
                                asigDto.setAsignatura(asignaturaEntity.getNombre());
                                asigDto.setCalificacion(null);
                                return asigDto;
                            })
                            .toList()
            );
        }

        return dto;
    }
}
