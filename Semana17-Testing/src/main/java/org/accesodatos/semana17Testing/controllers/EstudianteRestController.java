package org.accesodatos.semana17Testing.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana17Testing.dtos.request.create.EstudianteCreateDTO;
import org.accesodatos.semana17Testing.dtos.request.update.EstudianteUpdateDTO;
import org.accesodatos.semana17Testing.dtos.response.EstudianteDTO;
import org.accesodatos.semana17Testing.services.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST CONTROLLER DE ESTUDIANTES
 * ------------------------------
 * Gestiona el ciclo de vida completo (CRUD) de un estudiante.
 * Utiliza DTOs de entrada (Create/Update) y DTOs de salida (Response) para
 * mantener la integridad de la base de datos y la seguridad de la API.
 */

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor // LOMBOK: Genera el constructor con el 'estudianteService' final (Inyección de dependencias).
public class EstudianteRestController {

    private final EstudianteService estudianteService;

    /**
     * 1. GET: OBTENER TODOS
     * Devuelve una lista de DTOs. Si no hay, responde con 204 (No Content).
     */
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> obtenerTodos() {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodos();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estudiantes);
    }

    /**
     * 2. GET: OBTENER POR ID
     * @PathVariable: Mapea el {id} de la URL al parámetro del método.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerPorId(@PathVariable Long id) {
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorId(id);
        return ResponseEntity.ok(estudiante);
    }

    /**
     * 3. GET: BUSCAR POR NOMBRE
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<EstudianteDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantePorNombre(nombre);
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estudiantes);
    }

    /**
     * 4. POST: CREAR ESTUDIANTE
     * @Valid: Activa las validaciones (ej. @NotBlank) definidas en EstudianteCreateDTO.
     * @RequestBody: Indica que los datos vienen en el cuerpo de la petición JSON.
     */
    @PostMapping
    public ResponseEntity<EstudianteDTO> crearEstudiante(@Valid @RequestBody EstudianteCreateDTO dto) {
        EstudianteDTO estudianteCreado = estudianteService.crearEstudiante(dto);
        // CÓDIGO 201 (Created): Estándar REST para creaciones exitosas.
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteCreado);
    }

    /**
     * 5. PUT: ACTUALIZAR ESTUDIANTE
     * Actualiza un recurso existente basándose en el ID.
     * Usamos un DTO de Update diferente al de Create por si queremos restringir
     * qué campos se pueden editar (ej. no permitir cambiar la fecha de nacimiento).
     */
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @Valid @RequestBody EstudianteUpdateDTO dto) {
        EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, dto);
        return ResponseEntity.ok(estudianteActualizado);
    }

    /**
     * 6. DELETE (BORRADO EN CASCADA)
     * Debido a la configuración en la Entidad (CascadeType.ALL),
     * al borrar al estudiante aquí, se eliminará también su mascota en la BD automáticamente.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarEstudiante(@PathVariable Long id) {
        estudianteService.borrarEstudiante(id);
        return ResponseEntity.noContent().build();
    }
}