package org.accesodatos.semana13dto.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CasaDTO {
    private Long id;
    private String nombre;
    private String fundador;
    private String fantasma;
    private ProfesorDTO jefe;
    private List<String> estudiantes;
}
