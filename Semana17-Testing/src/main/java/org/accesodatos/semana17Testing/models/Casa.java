package org.accesodatos.semana17Testing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * ENTIDAD CASA (MODELO)
 * ---------------------
 * Esta clase mapea la tabla 'casa'. Es una entidad "contenedor",
 * ya que agrupa a los estudiantes y tiene asignado un profesor como jefe.
 */

@Data
@Entity
@Table(name = "casa")
public class Casa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casa")
    private Long idCasa;

    @Column(name = "nombre", unique = true, nullable = false, length = 50)
    // REGLA DE NEGOCIO: El nombre de la casa debe ser único (Gryffindor, Slytherin, etc.)
    private String nombre;

    @Column (nullable = false, length = 50)
    private String fundador;

    @Column (nullable = false, length = 50)
    private String fantasma;

    // --- RELACIONES ---

    /**
     * RELACIÓN 1:1 CON PROFESOR (Jefe de Casa)
     * Aquí la Casa es la DUEÑA de la relación porque tiene
     * el '@JoinColumn'. Esto significa que en la tabla SQL 'casa' habrá una
     * columna física llamada 'id_jefe' que apunta al profesor.
     */
    @OneToOne
    @JoinColumn(name = "id_jefe")
    @JsonBackReference("profesor-casa") // JACKSON: Evita que al mostrar al profesor, este vuelva a mostrar la casa, causando un bucle infinito.
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Profesor jefeCasa;

    /**
     * RELACIÓN 1:N CON ESTUDIANTE (Una casa tiene muchos estudiantes)
     * 'mappedBy = "casa"': Indica que la relación ya está definida en la clase Estudiante.
     * 'CascadeType.PERSIST/MERGE': Si guardamos una casa, se intentarán guardar los
     * estudiantes que tenga asociados, pero NO se borrarán en cascada si borramos la casa.
     */
    @OneToMany(mappedBy = "casa", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("casa-estudiante") // JACKSON: El lado que "manda". Al consultar la casa, veremos su lista de estudiantes.
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Estudiante> estudiantes;
}