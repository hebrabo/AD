package org.accesodatos.semana13dto.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EstudianteDTO {
    private Long id;
    private String nombre;
    private int anyoCurso;
    private LocalDate fechaNacimiento;
    private String casa;
    private MascotaDTO mascota;
    private List<AsignaturaCalificacionDTO> asignaturas;
}
