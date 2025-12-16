package org.accesodatos.hogwarts.model;

import jakarta.persistence.*; // Importa las herramientas de JPA (el estándar para bases de datos)
import lombok.Data;           // Importa la herramienta de Lombok

import java.time.LocalDate;

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

@Table(name = "estudiante")
// OPCIONAL PERO RECOMENDABLE:
// Si la clase se llama 'Estudiante' (mayúscula) y la tabla 'estudiante' (minúscula),
// a veces Spring se lía. Con esto le forzamos a buscar la tabla exacta "estudiante".
public class Estudiante {

    @Id
    // CLAVE PRIMARIA: Indica que este campo es la Primary Key (PK) de la tabla.
    // Es el DNI único de cada fila.

    @GeneratedValue
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


    @Column(name = "id_casa")
    // RELACIÓN SIMPLIFICADA:
    // En esta práctica, tratamos la Foreign Key simplemente como un número entero.
    // En el futuro, esto se sustituirá por un objeto de tipo 'Casa'.
    private Integer idCasa;


    @Column(name = "anyo_curso")
    // MAPEO DE NOMBRE: Conecta 'anyoCurso' (Java) con 'anyo_curso' (SQL).
    private Integer anyoCurso;


    @Column(name = "fecha_nacimiento", nullable = false)
    // TIPO DE DATO:
    // JPA convierte automáticamente el 'LocalDate' de Java al tipo 'DATE' de SQL.
    private LocalDate fechaNacimiento;
}