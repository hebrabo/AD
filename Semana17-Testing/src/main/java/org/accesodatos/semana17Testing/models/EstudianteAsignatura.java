package org.accesodatos.semana17Testing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

/**
 * ENTIDAD INTERMEDIA (RELACIÓN N:M CON ATRIBUTOS)
 * ----------------------------------------------
 * Esta clase rompe la relación "Muchos a Muchos" entre
 * Estudiante y Asignatura. Se crea como una entidad propia porque necesitamos
 * guardar un dato extra que no pertenece ni al alumno ni a la materia: la NOTA.
 */

@Entity
@Table(name = "Estudiante_Asignatura")
@Data
public class EstudianteAsignatura {

    @EmbeddedId // CLAVE COMPUESTA: Indica que la PK no es un solo campo, sino un objeto.
    private EstudianteAsignaturaKey id;

    @Column(name = "calificacion", precision = 3, scale = 1)
    // PRECISIÓN DECIMAL: 'precision 3, scale 1' significa que permite notas como 10.0 o 9.5
    // (3 dígitos en total, 1 de ellos decimal). En PostgreSQL se mapea como NUMERIC(3,1).
    private BigDecimal calificacion;

    // --- RELACIONES: MAPEADO DE LA CLAVE COMPUESTA ---

    /**
     * RELACIÓN CON ESTUDIANTE
     * @MapsId: Conecta este atributo con el campo 'estudianteId' dentro de la
     * clase EstudianteAsignaturaKey. Así, la PK de esta tabla es, a la vez, una FK.
     */
    @ManyToOne
    @MapsId("estudianteId")
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    /**
     * RELACIÓN CON ASIGNATURA
     * @MapsId: Conecta con 'asignaturaId' en la clave compuesta.
     */
    @ManyToOne
    @MapsId("asignaturaId")
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;
}