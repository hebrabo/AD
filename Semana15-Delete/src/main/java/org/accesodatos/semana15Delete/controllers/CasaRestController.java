package org.accesodatos.semana15Delete.controllers;

import org.accesodatos.semana15Delete.dtos.response.CasaDTO;
import org.accesodatos.semana15Delete.services.CasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/casas") // URL base: http://localhost:8080/api/casas
public class CasaRestController {

    private final CasaService casaService;

    @Autowired
    public CasaRestController(CasaService casaService) {
        this.casaService = casaService;
    }

    // 1. Obtener todas las casas
    @GetMapping
    public ResponseEntity<List<CasaDTO>> obtenerTodas() {
        List<CasaDTO> casas = casaService.obtenerTodas();

        if (casas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(casas);
    }

    // 2. Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<CasaDTO> obtenerPorId(@PathVariable Long id) {
        CasaDTO casa = casaService.obtenerCasaPorId(id);

        if (casa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(casa);
    }

    // 3. Buscar por nombre (ej: Gryffindor)
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<CasaDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<CasaDTO> casas = casaService.obtenerCasaPorNombre(nombre);

        if (casas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(casas);
    }

    // 4. Delete (SET NULL)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCasa(@PathVariable Long id) {
        casaService.borrarCasa(id);
        return ResponseEntity.noContent().build();
    }
}