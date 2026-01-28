package org.accesodatos.semana13dto.controllers;

import org.accesodatos.semana13dto.dtos.EstudianteDTO;
import org.accesodatos.semana13dto.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes") // URL base para los estudiantes
public class EstudianteRestController {

    private final EstudianteService estudianteService;

    @Autowired
    public EstudianteRestController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    // 1. Obtener todos los estudiantes
    // GET http://localhost:8080/api/estudiantes
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> obtenerTodos() {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodos();

        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 Sin contenido
        }
        return ResponseEntity.ok(estudiantes); // 200 OK
    }

    // 2. Obtener un estudiante por su ID
    // GET http://localhost:8080/api/estudiantes/5
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerPorId(@PathVariable Long id) {
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorId(id);

        if (estudiante == null) {
            return ResponseEntity.notFound().build(); // 404 No encontrado
        }
        return ResponseEntity.ok(estudiante); // 200 OK
    }

    // 3. Buscar estudiantes por nombre (o parte del nombre)
    // GET http://localhost:8080/api/estudiantes/nombre/Harry
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<EstudianteDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantePorNombre(nombre);

        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 Sin coincidencias
        }
        return ResponseEntity.ok(estudiantes); // 200 OK
    }
}