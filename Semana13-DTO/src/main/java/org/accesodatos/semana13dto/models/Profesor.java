package org.accesodatos.semana13dto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    // ----- RELACIONES ----

    @OneToOne
    @JoinColumn(name = "id_asignatura")
    @JsonBackReference("asignatura-profesor")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asignatura asignatura;

    @OneToOne(mappedBy = "jefeCasa", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("profesor-casa")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Casa casa;
}
