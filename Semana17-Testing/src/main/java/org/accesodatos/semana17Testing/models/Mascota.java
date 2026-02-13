package org.accesodatos.semana17Testing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Long idMascota;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String especie;

    // --- RELACIONES ---

    @OneToOne(optional = false)
    @JoinColumn(name = "id_estudiante")
    @JsonBackReference("estudiante-mascota")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Estudiante estudiante;
}
