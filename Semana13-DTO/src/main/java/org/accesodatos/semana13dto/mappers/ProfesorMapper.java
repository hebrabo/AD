package org.accesodatos.semana13dto.mappers;

import org.accesodatos.semana13dto.dtos.ProfesorDTO;
import org.accesodatos.semana13dto.models.Profesor;
import org.springframework.stereotype.Component;

@Component
public class ProfesorMapper {
    public ProfesorDTO toDto(Profesor profesor) {
        if (profesor == null) {
            return null;
        }
        ProfesorDTO dto = new ProfesorDTO();
        dto.setId(profesor.getId());
        dto.setNombre(profesor.getNombre() + " " + profesor.getApellido());
        if (profesor.getAsignatura() != null) {
            dto.setAsignatura(profesor.getAsignatura().getNombre());
        }
        dto.setFechaInicio(profesor.getFechaInicio());

        return dto;
    }
}
