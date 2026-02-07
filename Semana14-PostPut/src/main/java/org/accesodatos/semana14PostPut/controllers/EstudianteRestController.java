package org.accesodatos.semana14PostPut.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana14PostPut.dtos.request.create.EstudianteCreateDTO;
import org.accesodatos.semana14PostPut.dtos.request.update.EstudianteUpdateDTO;
import org.accesodatos.semana14PostPut.dtos.response.EstudianteDTO;
import org.accesodatos.semana14PostPut.services.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteRestController {

    private final EstudianteService estudianteService;

    // --- 1. GET: Obtener todos ---
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> obtenerTodos() {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodos();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(estudiantes); // 200 OK
    }

    // --- 2. GET: Obtener por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerPorId(@PathVariable Long id) {
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorId(id);
        return ResponseEntity.ok(estudiante); // 200 OK
    }

    // --- 3. GET: Buscar por nombre  ---
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<EstudianteDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantePorNombre(nombre);
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estudiantes);
    }

    // --- 4. POST: Crear Estudiante ---
    @PostMapping
    public ResponseEntity<EstudianteDTO> crearEstudiante(@Valid @RequestBody EstudianteCreateDTO dto) {
        EstudianteDTO estudianteCreado = estudianteService.crearEstudiante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteCreado); // 201 Created
    }

    // --- 5. PUT: Actualizar Estudiante ---
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @Valid @RequestBody EstudianteUpdateDTO dto) {
        EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, dto);
        return ResponseEntity.ok(estudianteActualizado); // 200 OK
    }
}