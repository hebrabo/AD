package org.accesodatos.semana13dto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "casa")
public class Casa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casa")
    private Long idCasa;

    @Column(name = "nombre", unique = true, nullable = false, length = 50)
    private String nombre;

    @Column (nullable = false, length = 50)
    private String fundador;

    @Column (nullable = false, length = 50)
    private String fantasma;

    // --- RELACIONES ---
    @OneToOne
    @JoinColumn(name = "id_jefe")
    @JsonBackReference("profesor-casa")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Profesor jefeCasa;


    @OneToMany(mappedBy = "casa", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("casa-estudiante")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Estudiante> estudiantes;

}