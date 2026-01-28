package org.accesodatos.semana13dto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "estudiante", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "apellido"})
})
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long idEstudiante;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(name = "anyo_curso")
    private Integer anyoCurso;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // --- RELACIONES ---

    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("estudiante-mascota")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_casa") //id_casa xk en el JSON Estudiante tiene este nombre
    @JsonBackReference("casa-estudiante")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Casa casa;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Estudiante_Asignatura",
            joinColumns = @JoinColumn(name = "id_estudiante"),
            inverseJoinColumns = @JoinColumn(name = "id_asignatura")
    )
    @JsonManagedReference("estudiante-asignatura")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Asignatura> asignaturas = new HashSet<>();
}
