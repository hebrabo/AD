package org.accesodatos.semana13dto.controllers;

import org.accesodatos.semana13dto.dtos.AsignaturaDTO;
import org.accesodatos.semana13dto.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas") // URL base: http://localhost:8080/api/asignaturas
public class AsignaturaRestController {

    private final AsignaturaService asignaturaService;

    @Autowired
    public AsignaturaRestController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    // 1. Obtener todas las asignaturas
    @GetMapping
    public ResponseEntity<List<AsignaturaDTO>> obtenerTodas() {
        List<AsignaturaDTO> asignaturas = asignaturaService.obtenerTodas();

        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asignaturas);
    }

    // 2. Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> obtenerPorId(@PathVariable Long id) {
        AsignaturaDTO asignatura = asignaturaService.obtenerAsignaturaPorId(id);

        if (asignatura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(asignatura);
    }

    // 3. Buscar por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<AsignaturaDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<AsignaturaDTO> asignaturas = asignaturaService.obtenerAsignaturaPorNombre(nombre);

        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asignaturas);
    }
}