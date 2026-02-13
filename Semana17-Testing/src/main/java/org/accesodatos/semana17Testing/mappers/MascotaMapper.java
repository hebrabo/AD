package org.accesodatos.semana17Testing.mappers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana17Testing.dtos.request.create.MascotaCreateDTO;
import org.accesodatos.semana17Testing.dtos.request.update.MascotaUpdateDTO;
import org.accesodatos.semana17Testing.dtos.response.MascotaDTO;
import org.accesodatos.semana17Testing.models.Mascota;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class MascotaMapper {

    // --- 1. De Entidad a DTO (GET) ---
    public MascotaDTO toDto(Mascota mascota) {
        if (mascota == null) return null;

        MascotaDTO dto = new MascotaDTO();
        dto.setId(mascota.getIdMascota());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());

        if (mascota.getEstudiante() != null) {
            dto.setEstudiante(
                    mascota.getEstudiante().getNombre() + " " + mascota.getEstudiante().getApellido()
            );
        } else {
            dto.setEstudiante(null);
        }
        return dto;
    }

    // --- 2. De CreateDTO a Entidad (POST) ---
    public Mascota toEntity(MascotaCreateDTO dto) {
        if (dto == null) return null;

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        return mascota;
    }

    // --- 3. De UpdateDTO a Entidad  ---
    public Mascota toEntity(MascotaUpdateDTO dto) {
        if (dto == null) return null;

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        return mascota;
    }

    // --- 4. Actualizar existente (PUT) ---
    public void updateMascotaFromDto(MascotaUpdateDTO dto, Mascota mascota) {
        if (dto == null || mascota == null) return;

        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
    }
}