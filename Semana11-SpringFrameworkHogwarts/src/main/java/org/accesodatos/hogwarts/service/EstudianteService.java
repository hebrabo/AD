package org.accesodatos.hogwarts.service;

import org.accesodatos.hogwarts.model.Estudiante;
import org.accesodatos.hogwarts.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CAPA DE SERVICIO (LÓGICA DE NEGOCIO)
 * -------------------------------------
 * Esta clase actúa como intermediario. Recibe las peticiones del Controlador (Controller),
 * aplica reglas de negocio (si las hubiera) y llama al Repositorio para hablar con la Base de Datos.
 */
@Service // 1. INDICA A SPRING QUE ESTO ES UN SERVICIO
// Spring registrará esta clase en su contenedor y la tendrá lista para usarla cuando la necesitemos.
public class EstudianteService {

    // Declaramos la interfaz del repositorio.
    // Es 'final' para asegurar que, una vez asignado, no cambie (inmutabilidad).
    private final EstudianteRepository estudianteRepository;

    /**
     * INYECCIÓN DE DEPENDENCIAS POR CONSTRUCTOR
     * -----------------------------------------
     * Tal como indicaban los apuntes: "es aconsejable la inyección por constructor".
     *
     * @param estudianteRepository Spring busca automáticamente el repositorio creado
     * y nos lo entrega (inyecta) aquí.
     */
    @Autowired // Opcional en versiones nuevas de Spring si solo hay un constructor, pero buena práctica ponerlo.
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * OBTENER TODOS (READ - Select All)
     * Llama al repositorio para traer todas las filas de la tabla 'estudiante'.
     * @return Una lista con todos los objetos Estudiante.
     */
    public List<Estudiante> obtenerTodos() {
        // findAll() es un método de JpaRepository.
        // Equivale a: SELECT * FROM estudiante;
        return estudianteRepository.findAll();
    }

    /**
     * OBTENER POR ID (READ - Select One)
     * Busca un estudiante específico por su clave primaria.
     *
     * @param id El ID del estudiante a buscar.
     * @return Un objeto 'Optional'. Puede contener el Estudiante
     * o estar vacío si no existe ese ID. Evita errores de tipo NullPointerException.
     */
    public Optional<Estudiante> obtenerPorId(Long id) {
        // Equivale a: SELECT * FROM estudiante WHERE id_estudiante = ?;
        return estudianteRepository.findById(id);
    }

    /**
     * GUARDAR O ACTUALIZAR (CREATE / UPDATE)
     * Este método sirve para dos cosas:
     * 1. Si el objeto 'estudiante' NO tiene ID (es null), crea uno nuevo (INSERT).
     * 2. Si el objeto 'estudiante' SÍ tiene ID, actualiza el existente (UPDATE).
     *
     * @param estudiante El objeto con los datos a guardar.
     * @return El estudiante guardado (incluyendo el ID generado automáticamente si es nuevo).
     */
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        // Aquí podríamos poner lógica extra antes de guardar (ej.: validar que sea mayor de edad).
        return estudianteRepository.save(estudiante);
    }

    /**
     * ELIMINAR (DELETE)
     * Borra un estudiante de la base de datos.
     *
     * @param id El ID del estudiante a eliminar.
     */
    public void eliminarEstudiante(Long id) {
        // Equivale a: DELETE FROM estudiante WHERE id_estudiante = ?;
        estudianteRepository.deleteById(id);
    }
}