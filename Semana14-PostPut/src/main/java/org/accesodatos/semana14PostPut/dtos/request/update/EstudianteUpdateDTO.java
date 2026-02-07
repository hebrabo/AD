package org.accesodatos.semana14PostPut.dtos.request.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EstudianteUpdateDTO {

    @NotNull(message = "El año del curso es obligatorio")
    @Min(value = 1, message = "El año mínimo es 1º")
    @Max(value = 7, message = "El año máximo es 7º")
    private Integer anyoCurso;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @Valid
    private MascotaUpdateDTO mascota;
}