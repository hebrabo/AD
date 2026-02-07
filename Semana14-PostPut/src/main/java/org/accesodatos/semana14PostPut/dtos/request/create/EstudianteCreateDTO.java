package org.accesodatos.semana14PostPut.dtos.request.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EstudianteCreateDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellido;

    @NotNull(message = "El año del curso es obligatorio")
    @Min(value = 1, message = "El año mínimo es 1º")
    @Max(value = 7, message = "El año máximo es 7º")
    private Integer anyoCurso;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El ID de la casa es obligatorio")
    private Long casaId;

    @Valid
    private MascotaCreateDTO mascota;
}