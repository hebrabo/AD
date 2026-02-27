package org.accesodatos.semana17Testing.mappers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana17Testing.dtos.request.create.MascotaCreateDTO;
import org.accesodatos.semana17Testing.dtos.request.update.MascotaUpdateDTO;
import org.accesodatos.semana17Testing.dtos.response.MascotaDTO;
import org.accesodatos.semana17Testing.models.Mascota;
import org.springframework.stereotype.Component;

/**
 * MAPPER DE MASCOTAS
 * ------------------
 * Esta clase transforma los datos de las mascotas.
 * Es clave para entender el "aplanamiento" de datos: convertimos un objeto
 * Estudiante en un simple String con su nombre.
 */

@Component
@Data
@RequiredArgsConstructor
public class MascotaMapper {

    /**
     * 1. DE ENTIDAD A DTO (FLUJO DE SALIDA - GET)
     * Convertimos la Mascota de la BD en algo que el usuario entienda.
     */
    public MascotaDTO toDto(Mascota mascota) {
        if (mascota == null) return null;

        MascotaDTO dto = new MascotaDTO();
        dto.setId(mascota.getIdMascota());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());

        /**
         * EVITAR RECURSIVIDAD Y PESO:
         * En lugar de meter todo el objeto Estudiante dentro del JSON de Mascota,
         * solo sacamos el nombre legible. Así evitamos bucles infinitos.
         */
        if (mascota.getEstudiante() != null) {
            dto.setEstudiante(
                    mascota.getEstudiante().getNombre() + " " + mascota.getEstudiante().getApellido()
            );
        } else {
            dto.setEstudiante(null);
        }
        return dto;
    }

    /**
     * 2. DE CREATEDTO A ENTIDAD (FLUJO DE ENTRADA - POST)
     * Usado cuando creamos una mascota desde cero.
     */
    public Mascota toEntity(MascotaCreateDTO dto) {
        if (dto == null) return null;

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        // El vínculo con el Estudiante se suele hacer en el Service o en el EstudianteMapper
        // para asegurar la integridad de la relación 1:1.
        return mascota;
    }

    /**
     * 3. DE UPDATEDTO A ENTIDAD (AUXILIAR)
     */
    public Mascota toEntity(MascotaUpdateDTO dto) {
        if (dto == null) return null;

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        return mascota;
    }

    /**
     * 4. ACTUALIZAR EXISTENTE (FLUJO DE EDICIÓN - PUT)
     * Este metodo "pisa" los datos de una mascota que ya existe.
     */
    public void updateMascotaFromDto(MascotaUpdateDTO dto, Mascota mascota) {
        if (dto == null || mascota == null) return;

        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
    }
}