package org.accesodatos.semana17Testing.controllers;

import org.accesodatos.semana17Testing.dtos.response.AsignaturaDTO;
import org.accesodatos.semana17Testing.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAPA DE CONTROLADOR (REST CONTROLLER)
 * -------------------------------------
 * Esta clase gestiona el protocolo HTTP. Transforma las
 * peticiones del usuario (URLs) en llamadas a la lógica de negocio (Servicios).
 */

@RestController // Indica que los datos devueltos serán JSON automáticamente.
@RequestMapping("/api/asignaturas") // Define el "punto de entrada" (Endpoint) base para este recurso.
public class AsignaturaRestController {

    // INYECCIÓN DE DEPENDENCIAS: El controlador no sabe "hacer" nada,
    // delega todo el trabajo al Servicio.
    private final AsignaturaService asignaturaService;

    @Autowired // Spring busca el componente 'AsignaturaService' y lo conecta aquí.
    public AsignaturaRestController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    /**
     * 1. OBTENER TODAS LAS ASIGNATURAS
     * Se usa @GetMapping para consultas de lectura.
     * ResponseEntity: Permite controlar no solo el contenido, sino el CÓDIGO DE ESTADO HTTP.
     */
    @GetMapping
    public ResponseEntity<List<AsignaturaDTO>> obtenerTodas() {
        List<AsignaturaDTO> asignaturas = asignaturaService.obtenerTodas();

        if (asignaturas.isEmpty()) {
            // CÓDIGO 204 (No Content): La petición es correcta pero no hay datos que enviar.
            return ResponseEntity.noContent().build();
        }
        // CÓDIGO 200 (OK): Envía la lista de DTOs en el cuerpo de la respuesta.
        return ResponseEntity.ok(asignaturas);
    }

    /**
     * 2. OBTENER POR ID
     * @PathVariable: Extrae el valor de la propia URL (ej: /api/asignaturas/5).
     */
    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> obtenerPorId(@PathVariable Long id) {
        AsignaturaDTO asignatura = asignaturaService.obtenerAsignaturaPorId(id);

        if (asignatura == null) {
            // CÓDIGO 404 (Not Found): El recurso solicitado no existe.
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(asignatura);
    }

    /**
     * 3. BUSCAR POR NOMBRE
     * Útil para búsquedas filtradas. Al devolver una lista, si no hay coincidencias
     * se prefiere el 204 (No Content) antes que un error.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<AsignaturaDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<AsignaturaDTO> asignaturas = asignaturaService.obtenerAsignaturaPorNombre(nombre);

        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asignaturas);
    }

    /**
     * 4. BORRAR ASIGNATURA
     * Se devuelve 204 (No Content) para confirmar que el recurso ya no está.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarAsignatura(@PathVariable Long id) {
        asignaturaService.borrarAsignatura(id);
        return ResponseEntity.noContent().build();
    }
}