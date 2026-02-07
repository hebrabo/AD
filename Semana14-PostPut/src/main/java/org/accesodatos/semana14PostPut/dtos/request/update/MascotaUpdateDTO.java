package org.accesodatos.semana14PostPut.dtos.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MascotaUpdateDTO {
    @NotBlank(message = "El nombre de la mascota no puede estar vacío")
    @Size(max = 50, message = "El nombre de la mascota no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "La especie de la mascota no puede estar vacía")
    @Size(max = 50, message = "La especie no puede superar los 50 caracteres")
    private String especie;
}