package org.accesodatos.semana17Testing.dtos.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AsignaturaCalificacionDTO {
    private String asignatura;
    private BigDecimal calificacion;
}