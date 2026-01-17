package org.accesodatos.hogwarts.controller;

import org.accesodatos.hogwarts.model.Casa;
import org.accesodatos.hogwarts.service.CasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/casas")
public class CasaController {

    @Autowired
    private CasaService casaService;

    @GetMapping
    public ResponseEntity<List<Casa>> listarCasas() {
        return ResponseEntity.ok(casaService.obtenerTodas());
    }
}