package org.accesodatos.semana15Delete.mappers;

import org.accesodatos.semana15Delete.dtos.response.AsignaturaDTO;
import org.accesodatos.semana15Delete.models.Asignatura;
import org.springframework.stereotype.Component;

@Component
public class AsignaturaMapper {
    public AsignaturaDTO toDto(Asignatura asignatura) {
        if (asignatura == null) {
            return null;
        }
        AsignaturaDTO dto = new AsignaturaDTO();
        dto.setId(asignatura.getId());
        dto.setNombre(asignatura.getNombre());
        dto.setAula(asignatura.getAula());
        dto.setObligatoria(asignatura.getObligatoria());
        if (asignatura.getProfesor() != null) {
            dto.setProfesor(
                    asignatura.getProfesor().getNombre() + " " + asignatura.getProfesor().getApellido()
            );
        }

        return dto;
    }
}
