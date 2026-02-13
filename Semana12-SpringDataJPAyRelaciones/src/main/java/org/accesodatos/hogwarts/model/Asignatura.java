package org.accesodatos.hogwarts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode; // <--- IMPORTANTE: Importar esto
import lombok.ToString;
import java.util.List;

@Data
@Entity
@Table(name = "asignatura")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignatura")
    private Long idAsignatura;

    @Column(name = "nombre_asignatura", unique = true, nullable = false, length = 100)
    private String nombre;

    private String aula;

    private Boolean obligatoria;

    // --- RELACIONES ---

    /* * RELACIÓN 1:1 CON PROFESOR
     * -------------------------
     * @OneToOne: Indica relación uno a uno.
     * mappedBy = "asignatura":
     * Significa "Yo NO soy el dueño de la relación".
     * Le dice a Spring: "Ve a la clase Profesor y busca la variable llamada 'asignatura'
     * para saber cómo unirte, porque él tiene la Foreign Key".
     * @JsonBackReference:
     * Evita la recursión infinita en JSON. Al serializar, se detiene aquí y
     * NO muestra al profesor para no entrar en bucle.
     * @ToString.Exclude y @EqualsAndHashCode.Exclude:
     * Evita bucles infinitos en la consola y errores de memoria (StackOverflow)
     * al calcular el hash del objeto.
     */
    @OneToOne(mappedBy = "asignatura")
    @JsonBackReference("profesor-asignatura")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude // <--- AÑADIDO: Vital para romper el bucle
    private Profesor profesor;

    /* * RELACIÓN N:M CON ESTUDIANTE
     * ---------------------------
     * @ManyToMany: Relación de muchos a muchos.
     * mappedBy = "asignaturas":
     * Indica que la configuración de la tabla intermedia (@JoinTable)
     * está definida en la variable 'asignaturas' de la clase Estudiante.
     * @JsonBackReference:
     * Al pedir una Asignatura, NO mostrará la lista de estudiantes para
     * proteger el rendimiento y evitar bucles.
     * @EqualsAndHashCode.Exclude:
     * OBLIGATORIO porque en Estudiante hemos usado un Set. Si no ponemos esto,
     * Java intentará comparar infinitamente estudiantes con asignaturas y explotará.
     */
    @ManyToMany(mappedBy = "asignaturas")
    @JsonBackReference("estudiante-asignatura")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Estudiante> estudiantes;
}