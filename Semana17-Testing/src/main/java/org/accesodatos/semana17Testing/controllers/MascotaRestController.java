package org.accesodatos.semana17Testing.controllers;

import org.accesodatos.semana17Testing.dtos.response.MascotaDTO;
import org.accesodatos.semana17Testing.services.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST CONTROLLER DE MASCOTAS
 * ---------------------------
 * Punto de acceso para la gestión de mascotas.
 * Sigue el patrón arquitectónico REST para ofrecer servicios de consulta
 * sobre la base de datos de forma segura y estandarizada.
 */

@RestController // Indica que la respuesta de cada método se serializará directamente a JSON.
@RequestMapping("/api/mascotas") // URL base para este recurso. Ejemplo: http://dominio-aws.com/api/mascotas
public class MascotaRestController {

    private final MascotaService mascotaService;

    @Autowired // Inyección por constructor: garantiza que el controlador tenga su servicio listo al arrancar.
    public MascotaRestController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    /**
     * 2. OBTENER TODAS LAS MASCOTAS
     * Se utiliza el verbo GET para peticiones de lectura.
     * ResponseEntity permite manejar la semántica HTTP.
     */
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> obtenerTodas() {
        List<MascotaDTO> mascotas = mascotaService.obtenerTodas();

        if (mascotas.isEmpty()) {
            // CÓDIGO 204: Petición exitosa, pero el servidor no tiene contenido que devolver.
            return ResponseEntity.noContent().build();
        }
        // CÓDIGO 200: Éxito total. El cuerpo contiene la lista de DTOs.
        return ResponseEntity.ok(mascotas);
    }

    /**
     * 3. OBTENER POR ID (Ej: /api/mascotas/5)
     * @PathVariable: Indica que el valor del ID viene incrustado en la ruta de la URL.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> obtenerPorId(@PathVariable Long id) {
        MascotaDTO mascota = mascotaService.obtenerMascotaPorId(id);

        if (mascota == null) {
            // CÓDIGO 404: El cliente pidió un ID que no existe. Es un error del lado del cliente.
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mascota);
    }

    /**
     * 4. BUSCAR POR NOMBRE (Ej: /api/mascotas/nombre/Hedwig)
     * Permite búsquedas parciales o exactas delegando en la lógica del Servicio.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<MascotaDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<MascotaDTO> mascotas = mascotaService.obtenerMascotasPorNombre(nombre);

        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mascotas);
    }
}