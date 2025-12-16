package org.accesodatos.hogwarts.repository;

import org.accesodatos.hogwarts.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CAPA DE REPOSITORIO (ACCESO A DATOS)
 * ------------------------------------
 * Esta interfaz es el puente directo con la Base de Datos.
 * Aquí no hay lógica de negocio, solo operaciones de lectura y escritura (CRUD).
 */
@Repository // Indica a Spring que esto es un componente de acceso a datos.
// Además, activa la traducción automática de errores de SQL a errores de Java.
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // -------------------------------------------------------------------------
    // ¿POR QUÉ ESTÁ VACÍO Y ES UNA INTERFAZ?
    // -------------------------------------------------------------------------
    // Al extender de 'JpaRepository', se heredan automáticamente más de 20 métodos
    // listos para usar sin escribir ni una sola línea de código SQL.

    // Los Genericos <Estudiante, Long> significan:
    // 1. Estudiante: Esta es la entidad (tabla) que vamos a gestionar.
    // 2. Long: Este es el tipo de dato de la Clave Primaria (@Id) de esa entidad.

    // MÉTODOS QUE YA TENEMOS AUNQUE NO LOS VEAMOS AQUÍ:
    // - findAll()      -> SELECT * FROM estudiante
    // - findById(id)   -> SELECT * FROM estudiante WHERE id = ?
    // - save(entity)   -> INSERT o UPDATE (dependiendo de si tiene ID)
    // - deleteById(id) -> DELETE FROM estudiante WHERE id = ?
    // - count()        -> SELECT COUNT(*) FROM estudiante

    // -------------------------------------------------------------------------
    // CONSULTAS PERSONALIZADAS (OPCIONAL)
    // -------------------------------------------------------------------------
    // Si quisieramos buscar por nombre, solo tendríamos que declarar el método así:
    // List<Estudiante> findByNombre(String nombre);
    //
    // Spring leería el nombre del método y escribiría el SQL automáticamente.
}