package org.accesodatos.semana17Testing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDate;

/**
 * ENTIDAD PROFESOR (MODELO)
 * -------------------------
 * Esta clase mapea la tabla 'profesor'. Representa al equipo
 * docente de Hogwarts. Es un ejemplo perfecto de cómo una entidad centraliza
 * diferentes relaciones 1:1 en el modelo relacional.
 */

@Data
@Entity
@Table(name = "Profesor")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor") // PK: Identificador único del profesor.
    private Long id;

    @Column(nullable = false, length = 50) // Restricción: No nulo y máximo 50 caracteres.
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(name = "fecha_inicio") // Mapeo de fecha: Hibernate lo traduce a tipo DATE en SQL.
    private LocalDate fechaInicio;

    // ----- RELACIONES ----

    /**
     * RELACIÓN 1:1 CON ASIGNATURA
     * Aquí el Profesor es el DUEÑO de la relación.
     * '@JoinColumn': Crea la Foreign Key física 'id_asignatura' en la tabla profesor.
     * Un profesor solo imparte una asignatura específica.
     */
    @OneToOne
    @JoinColumn(name = "id_asignatura")
    @JsonBackReference("asignatura-profesor") // JACKSON: Evita que al mostrar la asignatura, se cree un bucle infinito volviendo al profesor.
    @ToString.Exclude // LOMBOK: Evita que el metodo .toString() falle por referencias circulares.
    @EqualsAndHashCode.Exclude
    private Asignatura asignatura;

    /**
     * RELACIÓN 1:1 CON CASA (Jefe de Casa)
     * 'mappedBy = "jefeCasa"': Indica que el Profesor NO es el dueño aquí.
     * EXPLICACIÓN: La Foreign Key real está en la tabla 'Casa'.
     * Esta relación permite que desde un profesor podamos saber qué casa dirige.
     */
    @OneToOne(mappedBy = "jefeCasa", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("profesor-casa") // JACKSON: Lado dominante. Al consultar un profesor, veremos los datos de la casa que dirige.
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Casa casa;
}