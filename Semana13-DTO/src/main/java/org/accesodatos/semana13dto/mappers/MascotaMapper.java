package org.accesodatos.semana13dto.mappers;

import org.accesodatos.semana13dto.dtos.MascotaDTO;
import org.accesodatos.semana13dto.models.Mascota;
import org.springframework.stereotype.Component;

@Component
public class MascotaMapper {
    public MascotaDTO toDto(Mascota mascota) {
        if (mascota == null) {
            return null;
        }
        MascotaDTO dto = new MascotaDTO();
        dto.setId(mascota.getIdMascota());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());

        // La tarea pide el nombre completo (nombre+apellido) del estudiante
        if (mascota.getEstudiante() != null) {
            dto.setEstudiante(
                    mascota.getEstudiante().getNombre() + " " + mascota.getEstudiante().getApellido()
            );
        }
        return dto;
    }
}
