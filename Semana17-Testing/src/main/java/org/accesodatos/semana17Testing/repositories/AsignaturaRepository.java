package org.accesodatos.semana17Testing.repositories;

import org.accesodatos.semana17Testing.models.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CAPA DE PERSISTENCIA (REPOSITORY)
 * ---------------------------------
 * Esta interfaz es la encargada de las operaciones CRUD
 * (Create, Read, Update, Delete) sobre la tabla 'asignatura'.
 * Al heredar de JpaRepository, Spring genera automáticamente toda la lógica de SQL.
 */

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    // JpaRepository<Entidad, Tipo_de_PK>
    // - Entidad: Asignatura
    // - Tipo_de_PK: Long (el ID de la asignatura)

    /**
     * QUERY METHODS (MÉTODOS DE CONSULTA)
     * -----------------------------------
     * Hemos definido un metodo personalizado usando la convención
     * de nombres de Spring Data JPA. No necesitamos escribir "SELECT * FROM...",
     * Spring interpreta el nombre del metodo:
     * * - findByNombre: Busca por la columna 'nombre'.
     * - Containing: Equivale al operador LIKE '%nombre%' de SQL.
     * - IgnoreCase: No distingue entre mayúsculas y minúsculas (ej: 'pociones' = 'Pociones').
     */
    List<Asignatura> findByNombreContainingIgnoreCase(String nombre);
}