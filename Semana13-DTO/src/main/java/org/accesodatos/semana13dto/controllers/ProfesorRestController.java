package org.accesodatos.semana13dto.controllers;

import org.accesodatos.semana13dto.dtos.ProfesorDTO;
import org.accesodatos.semana13dto.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesores") // URL base: http://localhost:8080/api/profesores
public class ProfesorRestController {

    private final ProfesorService profesorService;

    @Autowired
    public ProfesorRestController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    // 1. Obtener todos los profesores
    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> obtenerTodos() {
        List<ProfesorDTO> profesores = profesorService.obtenerTodos();

        if (profesores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profesores);
    }

    // 2. Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> obtenerPorId(@PathVariable Long id) {
        ProfesorDTO profesor = profesorService.obtenerProfesorPorId(id);

        if (profesor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profesor);
    }

    // 3. Buscar por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ProfesorDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<ProfesorDTO> profesores = profesorService.obtenerProfesorPorNombre(nombre);

        if (profesores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profesores);
    }
}