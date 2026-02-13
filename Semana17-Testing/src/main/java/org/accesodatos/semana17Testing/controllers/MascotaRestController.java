package org.accesodatos.semana17Testing.controllers;

import org.accesodatos.semana17Testing.dtos.response.MascotaDTO;
import org.accesodatos.semana17Testing.services.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas") // 1. La URL base para mascotas
public class MascotaRestController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaRestController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // 2. GET /api/mascotas
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> obtenerTodas() {
        List<MascotaDTO> mascotas = mascotaService.obtenerTodas();

        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 Si la lista está vacía
        }
        return ResponseEntity.ok(mascotas); // 200 Si hay datos
    }

    // 3. GET /api/mascotas/{id} (Ej: /api/mascotas/5)
    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> obtenerPorId(@PathVariable Long id) {
        MascotaDTO mascota = mascotaService.obtenerMascotaPorId(id);

        if (mascota == null) {
            return ResponseEntity.notFound().build(); // 404 Si no existe
        }
        return ResponseEntity.ok(mascota); // 200 Si existe
    }

    // 4. GET /api/mascotas/nombre/{nombre} (Ej: /api/mascotas/nombre/Hedwig)
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<MascotaDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<MascotaDTO> mascotas = mascotaService.obtenerMascotasPorNombre(nombre);

        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 Si no hay coincidencias
        }
        return ResponseEntity.ok(mascotas);
    }
}