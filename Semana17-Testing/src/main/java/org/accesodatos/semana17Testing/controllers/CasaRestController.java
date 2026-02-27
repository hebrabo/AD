package org.accesodatos.semana17Testing.controllers;

import org.accesodatos.semana17Testing.dtos.response.CasaDTO;
import org.accesodatos.semana17Testing.services.CasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST CONTROLLER DE CASAS
 * ------------------------
 * Proporciona los puntos de entrada para gestionar la información
 * de las casas (Gryffindor, Slytherin, etc.). Es la capa encargada de orquestar
 * la comunicación entre el cliente y el servicio de lógica de negocio.
 */

@RestController // Define que esta clase devolverá datos directamente (JSON) en el cuerpo de la respuesta.
@RequestMapping("/api/casas") // Define el recurso base. URL: http://localhost:8080/api/casas
public class CasaRestController {

    private final CasaService casaService;

    @Autowired // Inyección de dependencias por constructor: la mejor práctica para asegurar que el servicio no sea nulo.
    public CasaRestController(CasaService casaService) {
        this.casaService = casaService;
    }

    /**
     * 1. OBTENER TODAS LAS CASAS
     * EXPLICACIÓN: Se usa ResponseEntity para envolver la lista de DTOs.
     * Si la lista está vacía, se devuelve un 204 (No Content).
     */
    @GetMapping
    public ResponseEntity<List<CasaDTO>> obtenerTodas() {
        List<CasaDTO> casas = casaService.obtenerTodas();

        if (casas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(casas);
    }

    /**
     * 2. OBTENER POR ID
     * @PathVariable: Permite capturar variables dinámicas directamente desde la ruta de la URL.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CasaDTO> obtenerPorId(@PathVariable Long id) {
        CasaDTO casa = casaService.obtenerCasaPorId(id);

        if (casa == null) {
            // CÓDIGO 404: Respuesta estándar cuando el ID buscado no existe en la BD.
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(casa);
    }

    /**
     * 3. BUSCAR POR NOMBRE
     * Permite búsquedas específicas como /api/casas/nombre/Gryffindor.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<CasaDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<CasaDTO> casas = casaService.obtenerCasaPorNombre(nombre);

        if (casas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(casas);
    }

    /**
     * 4. BORRAR CASA (Estrategia SET NULL)
     * Al borrar una Casa, los estudiantes
     * asociados NO se borran (a diferencia del borrado en cascada), sino que
     * su campo 'id_casa' se pondrá a NULL para mantener la integridad.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCasa(@PathVariable Long id) {
        casaService.borrarCasa(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 confirmando el éxito.
    }
}