package org.accesodatos.hogwarts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Long idMascota;

    @Column(name = "nombre_mascota", nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String especie;

    // --- RELACIONES ---

    /* * RELACIÓN 1:1 CON ESTUDIANTE
     * ----------------------------
     * @OneToOne: Relación uno a uno.
     * @JoinColumn(name = "id_estudiante"):
     * Indica que la tabla 'mascota' es la PROPIETARIA física de la relación.
     * En el Script SQL, la Foreign Key 'id_estudiante' está
     * escrita dentro de la tabla 'Mascota'. Por eso ponemos @JoinColumn aquí
     * y no en la clase Estudiante.
     * @JsonBackReference:
     * Como el Estudiante es la entidad "Principal" (la que manda),
     * la Mascota debe ocultar al estudiante en el JSON para evitar el bucle infinito:
     * Estudiante -> Mascota -> Estudiante -> Mascota...
     */
    @OneToOne
    @JoinColumn(name = "id_estudiante")
    @JsonBackReference("estudiante-mascota")
    @ToString.Exclude
    private Estudiante estudiante;
}