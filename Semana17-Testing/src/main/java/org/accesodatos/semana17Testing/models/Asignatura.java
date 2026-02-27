package org.accesodatos.semana17Testing.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * ENTIDAD ASIGNATURA
 * ------------------
 * Esta entidad representa una de las tablas maestras de Hogwarts.
 * Es una entidad independiente que actúa como eje para Profesores y Calificaciones.
 */


@Data // LOMBOK: Genera automáticamente el código repetitivo (Getters, Setters, HashCode).
@Entity // JPA: Marca esta clase para que Hibernate cree la tabla 'asignatura' en la BD.
@Table(name = "asignatura")
public class Asignatura {

    @Id // PRIMARY KEY: El identificador único en la tabla.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SERIAL: La BD asigna el número automáticamente (1, 2, 3...).
    @Column(name="id_asignatura")
    private Long id;

    @Column(name = "nombre", unique = true, nullable = false, length = 100)
    // REGLA DE INTEGRIDAD: 'unique = true' impide que existan dos asignaturas con el mismo nombre (ej. no puede haber dos "Pociones").
    private String nombre;

    @Column(nullable = false, length = 50) // 'nullable = false' obliga a que siempre se asigne un aula.
    private String aula;

    @Column(nullable = false) // Mapea el tipo Boolean de Java al tipo BOOLEAN/BIT de SQL.
    private Boolean obligatoria;

    // ----- RELACIONES ----

    /**
     * RELACIÓN 1:1 CON PROFESOR
     * 'mappedBy': Indica que la clave foránea (FK) está en la tabla 'profesor'.
     * 'cascade': PERSIST y MERGE aseguran que si guardamos una asignatura con un profesor nuevo,
     * el profesor también se guarde automáticamente.
     */
    @OneToOne(mappedBy = "asignatura", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("asignatura-profesor") // JACKSON: Evita bucles infinitos en el JSON (Lado que "muestra" al profesor).
    @ToString.Exclude // SEGURIDAD: Evita que el metodo toString() falle por recursividad con el Profesor.
    @EqualsAndHashCode.Exclude
    private Profesor profesor;

    /**
     * RELACIÓN 1:N CON ESTUDIANTE_ASIGNATURA
     * Esta es la relación hacia la tabla de "notas". Una asignatura tiene muchas calificaciones.
     */
    @OneToMany(mappedBy = "asignatura")
    @JsonManagedReference("asignatura-calificaciones")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EstudianteAsignatura> calificaciones = new HashSet<>();
}
