package org.accesodatos.semana17Testing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ENTIDAD MASCOTA (MODELO)
 * ------------------------
 * Esta clase mapea la tabla 'mascota'. Representa una relación
 * de dependencia total, ya que en nuestro modelo, una mascota no existe sin un
 * estudiante que la posea.
 */

@Data
@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota") // PK: Identificador único de la mascota.
    private Long idMascota;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String especie;

    // --- RELACIONES ---

    /**
     * RELACIÓN 1:1 CON ESTUDIANTE
     * 'optional = false': Indica que no puede existir una mascota "huérfana" en la BD.
     * '@JoinColumn': Crea la Foreign Key física 'id_estudiante' en la tabla mascota.
     * EXPLICACIÓN: Mascota es la "propietaria" de la relación a nivel de base
     * de datos porque es donde reside la columna de unión (FK).
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "id_estudiante")
    @JsonBackReference("estudiante-mascota") // JACKSON: Lado secundario. Evita que la mascota vuelva a serializar al estudiante en el JSON.
    @ToString.Exclude // LOMBOK: Evita recursividad infinita al imprimir el objeto por consola.
    @EqualsAndHashCode.Exclude
    private Estudiante estudiante;
}