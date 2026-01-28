package org.accesodatos.semana13dto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "asignatura")
public class Asignatura {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_asignatura")
    private Long id;

    @Column(name = "nombre", unique = true, nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String aula;

    @Column(nullable = false)
    private Boolean obligatoria;

    // ----- RELACIONES ----

    @OneToOne(mappedBy = "asignatura", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("asignatura-profesor")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Profesor profesor;

    @ManyToMany(mappedBy = "asignaturas")
    @JsonBackReference("estudiante-asignatura")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Estudiante> estudiantes;
}
