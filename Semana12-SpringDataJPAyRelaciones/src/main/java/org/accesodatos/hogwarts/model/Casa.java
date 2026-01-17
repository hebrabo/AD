package org.accesodatos.hogwarts.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "casa")
public class Casa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casa")
    private Long idCasa;

    @Column(name = "nombre_casa", unique = true, nullable = false, length = 50)
    private String nombre;

    @Column (nullable = false, length = 50)
    private String fundador;

    @Column (nullable = false, length = 50)
    private String fantasma;

    // --- RELACIONES ---

    /* * RELACIÓN 1:1 CON PROFESOR (Jefe de Casa)
     * ------------------------------------------
     * @OneToOne: Un jefe por casa.
     * @JoinColumn(name = "id_jefe"):
     * IMPORTANTE: Aquí usamos @JoinColumn porque la tabla SQL 'Casa'
     * TIENE físicamente la columna 'id_jefe'. Es la dueña de la relación.
     * nullable = false:
     * Validamos que una casa no puede existir sin un jefe asignado (según el SQL).
     * @JsonManagedReference:
     * Al pedir la Casa, queremos ver quién es su jefe.
     * (Es la "Entidad Principal").
     */
    @OneToOne
    @JoinColumn(name = "id_jefe", nullable = false)
    @JsonManagedReference("casa-profesor")
    private Profesor jefeCasa;

    /* * RELACIÓN 1:N CON ESTUDIANTE
     * -----------------------------
     * @OneToMany: Una casa tiene MUCHOS estudiantes.
     * mappedBy = "casa":
     * La tabla 'Casa' NO tiene columnas de estudiantes. Son los estudiantes
     * los que tienen la FK 'id_casa'. Por eso decimos: "Ve a la clase Estudiante
     * y busca la variable 'casa' para saber quiénes son mis alumnos".
     * @JsonManagedReference:
     * Al pedir la Casa, queremos ver la lista completa de sus estudiantes.
     * * NOTA: Aquí NO ponemos @ToString.Exclude. Como esta es la entidad principal,
     * está bien que imprima a sus hijos. Los hijos son los que deben cortar
     * la impresión para no volver a subir.
     */
    @OneToMany(mappedBy = "casa")
    @JsonManagedReference("casa-estudiante")
    private List<Estudiante> estudiantes;
}