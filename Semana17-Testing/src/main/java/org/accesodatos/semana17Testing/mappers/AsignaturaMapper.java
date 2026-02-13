package org.accesodatos.semana17Testing.mappers;

import org.accesodatos.semana17Testing.dtos.response.AsignaturaDTO;
import org.accesodatos.semana17Testing.models.Asignatura;
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
