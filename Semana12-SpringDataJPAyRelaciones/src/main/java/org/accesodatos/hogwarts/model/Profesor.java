package org.accesodatos.hogwarts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
/*
 * RESTRICCIÓN DE UNICIDAD COMPUESTA
 * ---------------------------------
 * @Table: Define el nombre de la tabla SQL "profesor".
 * uniqueConstraints:
 * Traduce la regla SQL "UNIQUE (nombre, apellido)".
 * Impide que existan dos registros con el mismo nombre y apellido a la vez.
 * Es la forma correcta de mapear claves únicas compuestas en JPA.
 */
@Table(name = "profesor", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "apellido"})
})
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Long idProfesor;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    // --- RELACIONES ---

    /* * RELACIÓN 1:1 CON ASIGNATURA (Principal)
     * -----------------------------------------
     * @OneToOne: Relación uno a uno.
     * @JoinColumn(name = "id_asignatura"):
     * IMPORTANTE: Aquí usamos @JoinColumn porque la tabla 'Profesor'
     * del SQL tiene la columna física 'id_asignatura'.
     * Por tanto, Profesor es el "Dueño" físico de esta relación.
     * @JsonManagedReference:
     * Al pedir un profesor, queremos ver qué asignatura imparte.
     */
    @OneToOne
    @JoinColumn(name = "id_asignatura")
    @JsonManagedReference("profesor-asignatura")
    private Asignatura asignatura;

    /* * RELACIÓN 1:1 CON CASA (Secundaria)
     * ------------------------------------
     * @OneToOne(mappedBy = "jefeCasa"):
     * Aquí usamos mappedBy porque la tabla 'Profesor' NO tiene la columna de la Casa.
     * La columna 'id_jefe' está en la tabla 'Casa'. Le decimos a JPA: "Busca
     * la variable 'jefeCasa' en la entidad Casa para unirte".
     * @JsonBackReference:
     * La Casa es la entidad suprema. Si mostramos la casa aquí,
     * entraríamos en bucle (Casa -> Jefe -> Casa...). Ocultamos la casa.
     * @ToString.Exclude:
     * Obligatorio con Lombok para evitar bucles infinitos en la consola.
     */
    @OneToOne(mappedBy = "jefeCasa")
    @JsonBackReference("casa-profesor")
    @ToString.Exclude
    private Casa casa;
}