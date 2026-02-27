package org.accesodatos.semana17Testing.services;

import org.accesodatos.semana17Testing.dtos.request.create.EstudianteCreateDTO;
import org.accesodatos.semana17Testing.dtos.request.update.EstudianteUpdateDTO;
import org.accesodatos.semana17Testing.dtos.response.EstudianteDTO;

import java.util.List;

/**
 * INTERFAZ DE SERVICIO: ESTUDIANTE
 * -------------------------------
 * Esta interfaz representa el catálogo de operaciones de
 * negocio para los estudiantes. Sigue el principio de Abstracción: el
 * controlador sabe "qué" se puede hacer, pero no "cómo" se hace.
 */
public interface EstudianteService {

    /**
     * OPERACIONES DE LECTURA (GET)
     * Siempre devuelven DTOs para proteger la integridad del modelo.
     */
    List<EstudianteDTO> obtenerTodos();

    EstudianteDTO obtenerEstudiantePorId(Long id);

    List<EstudianteDTO> obtenerEstudiantePorNombre(String nombre);

    /**
     * OPERACIONES DE ESCRITURA (POST / PUT)
     * Utilizamos DTOs de Request específicos para crear
     * y para actualizar. Esto permite que las reglas de entrada sean
     * diferentes (ej: en el Create pedimos nombre, en el Update quizás no).
     */
    EstudianteDTO crearEstudiante(EstudianteCreateDTO dto);

    EstudianteDTO actualizarEstudiante(Long id, EstudianteUpdateDTO dto);

    /**
     * OPERACIÓN DE BORRADO (DELETE)
     * A diferencia de las Casas, aquí la estrategia suele
     * ser CASCADA. Al borrar un estudiante, se borrará su mascota vinculada
     * automáticamente en la base de datos para no dejar datos huérfanos.
     */
    void borrarEstudiante(Long id);
}