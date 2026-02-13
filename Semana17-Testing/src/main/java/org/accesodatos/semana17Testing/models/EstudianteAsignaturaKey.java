package org.accesodatos.semana17Testing.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class EstudianteAsignaturaKey implements Serializable {

    @Column(name = "id_estudiante")
    private Long estudianteId;

    @Column(name = "id_asignatura")
    private Long asignaturaId;
}