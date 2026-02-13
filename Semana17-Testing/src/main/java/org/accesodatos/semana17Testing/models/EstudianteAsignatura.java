package org.accesodatos.semana17Testing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "Estudiante_Asignatura")
@Data
public class EstudianteAsignatura {

    @EmbeddedId
    private EstudianteAsignaturaKey id;

    @Column(name = "calificacion", precision = 3, scale = 1)
    private BigDecimal calificacion;

    // --- RELACIONES ---
    @ManyToOne
    @MapsId("estudianteId")
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("asignaturaId")
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;


}