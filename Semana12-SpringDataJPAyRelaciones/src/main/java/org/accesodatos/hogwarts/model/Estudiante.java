package org.accesodatos.hogwarts.model;

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
/*
 * RESTRICCIÓN DE UNICIDAD COMPUESTA
 * ---------------------------------
 * @Table: Define el nombre real de la tabla en PostgreSQL ("estudiante").
 * uniqueConstraints:
 * Esto es vital. Le dice a la base de datos que no se permite tener dos filas
 * donde la combinación de "nombre" y "apellido" sea idéntica.
 * Ejemplo: Puedes tener dos "Harry", pero no dos "Harry Potter".
 */
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

    /* * RELACIÓN N:1 CON CASA (Muchos estudiantes -> Una Casa)
     * --------------------------------------------------------
     * @ManyToOne:
     * Indica la cardinalidad. Varios alumnos pertenecen a la misma casa.
     * @JoinColumn(name = "id_casa"):
     * IMPORTANTE. Esto indica que ESTA tabla ("estudiante") es la dueña de la relación
     * porque contiene físicamente la columna de clave foránea (FK) "id_casa".
     * @JsonBackReference:
     * Al serializar el Estudiante, NO queremos volver a mostrar todos los datos de la Casa
     * (y sus cientos de alumnos) para evitar bucles infinitos.
     * @ToString.Exclude y @EqualsAndHashCode.Exclude:
     * Evitan que Lombok entre en un bucle infinito al intentar imprimir el objeto
     * o calcular su identificador único.
     */
    @ManyToOne
    @JoinColumn(name = "id_casa")
    @JsonBackReference("casa-estudiante")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Casa casa;

    /* * RELACIÓN 1:1 CON MASCOTA
     * --------------------------
     * @OneToOne(mappedBy = "estudiante"):
     * mappedBy significa "Yo no tengo la clave foránea". Le dice a JPA: "Ve a la
     * clase Mascota y busca el atributo 'estudiante' para saber cómo unirnos".
     * (La tabla Mascota es la que tiene la columna 'id_estudiante').
     * @JsonManagedReference:
     * Cuando pidamos un estudiante, SÍ queremos ver su mascota
     * anidada en el JSON.
     */
    @OneToOne(mappedBy = "estudiante")
    @JsonManagedReference("estudiante-mascota")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Mascota mascota;

    /* * RELACIÓN N:M CON ASIGNATURA
     * -----------------------------
     * @ManyToMany: Relación muchos a muchos.
     * @JoinTable:
     * Configuración obligatoria en N:M para definir la TABLA INTERMEDIA.
     * - name: "estudiante_asignatura" -> Nombre real de la tabla intermedia en SQL.
     * - joinColumns: La columna FK que me apunta a MÍ (id_estudiante).
     * - inverseJoinColumns: La columna FK que apunta al OTRO lado (id_asignatura).
     * @JsonManagedReference:
     * Queremos ver la lista de asignaturas dentro del JSON.
     * * NOTA TÉCNICA: Usamos 'Set' en lugar de 'List' para evitar que salgan asignaturas
     * duplicadas en el JSON. Al usar Set, es OBLIGATORIO poner @EqualsAndHashCode.Exclude.
     */
    @ManyToMany
    @JoinTable(
            name = "estudiante_asignatura",
            joinColumns = @JoinColumn(name = "id_estudiante"),
            inverseJoinColumns = @JoinColumn(name = "id_asignatura")
    )
    @JsonManagedReference("estudiante-asignatura")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Asignatura> asignaturas = new HashSet<>();

}