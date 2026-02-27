package org.accesodatos.semana17Testing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * CAPA DE MODELO (ENTIDAD)
 * ------------------------
 * Esta clase es el "plano" de los datos. Gracias a JPA, Spring Boot cogerá esta clase
 * y sabrá cómo leer y escribir filas en la tabla 'estudiante' de la base de datos.
 */

@Data
// IMPORTANTE: Esta anotación de Lombok genera automáticamente los Getters, Setters,
// toString(), equals() y hashCode().
// GRACIAS A ESTO: No necesitamos escribir 'public String getNombre()...' manualmente.
// Si quitamos esto, Postman devolverá objetos vacíos {} porque no podría leer los datos.

@Entity
// OBLIGATORIO: Le dice a Spring "Esta clase no es código normal, representa una fila de la BD".

@Table(name = "estudiante", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "apellido"})
})
public class Estudiante {

    @Id
    // CLAVE PRIMARIA: Indica que este campo es la Primary Key (PK) de la tabla.
    // Es el DNI único de cada fila.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-INCREMENTAL:
    // Le dice a Java: "No te preocupes por inventar el ID, la base de datos lo generará".
    // Esto es necesario porque el SQL usa el tipo 'SERIAL'.

    @Column(name = "id_estudiante")
    // MAPEO DE NOMBRE:
    // En Java usamos camelCase (idEstudiante), pero el SQL usa snake_case (id_estudiante).
    // Esta línea conecta el nombre de Java con el nombre real de la columna en SQL.

    private Long idEstudiante;

    @Column(nullable = false, length = 50)
    // RESTRICCIONES:
    // nullable = false --> Equivale a NOT NULL en SQL.
    // length = 50      --> Equivale a VARCHAR(50).
    // Sirve para que Hibernate valide los datos antes de intentar guardarlos.

    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(name = "anyo_curso")
    private Integer anyoCurso;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // --- RELACIONES ---

    /* RELACIÓN 1:1 CON MASCOTA
        'mappedBy': Indica que la dueña de la relación (quien tiene la FK) es la clase Mascota.
        'CascadeType.ALL': Si borro al Estudiante, se borra automáticamente su Mascota (Persistencia en cascada).
        'orphanRemoval': Si quito la mascota de la lista, se elimina físicamente de la BD.
     */
    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("estudiante-mascota") // JACKSON: Evita la recursividad infinita al convertir a JSON (Lado que "manda" los datos).
    @ToString.Exclude // EVITA BUCLES: Impide que el metodo toString() entre en un bucle infinito con la Mascota.
    @EqualsAndHashCode.Exclude
    private Mascota mascota;

    /* RELACIÓN N:1 CON CASA (Muchos estudiantes viven en 1 casa)
       'FetchType.LAZY': Carga perezosa. No trae los datos de la Casa de la BD a menos que se pidan explícitamente (Mejora el rendimiento).
       '@JoinColumn': Aquí se crea físicamente la Foreign Key (id_casa) en la tabla estudiante.
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_casa")
    @JsonBackReference("casa-estudiante") // JACKSON: Lado que "se oculta" en el JSON para no repetir la información de la casa en bucle.
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Casa casa;

    /* RELACIÓN N:N CON ASIGNATURAS (A través de una tabla intermedia con atributos)
       En este caso, se usa 'EstudianteAsignatura' porque la relación tiene un atributo extra: la 'calificación'.
    */
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("estudiante-calificaciones")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EstudianteAsignatura> calificaciones = new HashSet<>(); // Usamos Set para evitar duplicados
}
