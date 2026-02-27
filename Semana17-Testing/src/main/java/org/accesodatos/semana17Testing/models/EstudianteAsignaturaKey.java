package org.accesodatos.semana17Testing.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

/**
 * CLAVE COMPUESTA (EMBEDDABLE)
 * ----------------------------
 * Esta clase no es una tabla en sí misma, sino que define
 * la estructura de la Clave Primaria de la tabla 'Estudiante_Asignatura'.
 * Se llama "compuesta" porque depende de dos valores para ser única.
 */

@Data
@Embeddable // JPA: Indica que esta clase será "empotrada" dentro de otra entidad como su ID.
public class EstudianteAsignaturaKey implements Serializable {
    // Serializable: Es obligatorio para las claves compuestas. Permite que Hibernate
    // pueda guardar el estado de la clave en caché o pasarla por la red.

    @Column(name = "id_estudiante")
    private Long estudianteId;

    @Column(name = "id_asignatura")
    private Long asignaturaId;

    /**
     * IMPORTANTE:
     * Para que una clave compuesta funcione correctamente en Hibernate, la clase DEBE:
     * 1. Ser @Embeddable.
     * 2. Implementar Serializable.
     * 3. Tener implementados los métodos equals() y hashCode() (Lombok @Data lo hace por nosotros).
     * Esto es necesario para que Hibernate pueda comparar si dos registros de notas son el mismo.
     */
}