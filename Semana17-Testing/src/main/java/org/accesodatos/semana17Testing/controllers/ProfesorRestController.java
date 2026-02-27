package org.accesodatos.semana17Testing.controllers;

import org.accesodatos.semana17Testing.dtos.response.ProfesorDTO;
import org.accesodatos.semana17Testing.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST CONTROLLER DE PROFESORES
 * ----------------------------
 * Esta clase actúa como el punto de entrada (Endpoint) para los
 * recursos de tipo Profesor. Su responsabilidad es recibir las peticiones HTTP y
 * devolver la información procesada en formato JSON a través de DTOs.
 */

@RestController // Especialización de @Component que combina @Controller y @ResponseBody.
@RequestMapping("/api/profesores") // URL base para el recurso. Ejemplo: http://localhost:8080/api/profesores
public class ProfesorRestController {

    // CAPA DE SERVICIO: Se declara como 'final' para garantizar la inmutabilidad.
    private final ProfesorService profesorService;

    @Autowired
    /**
     * INYECCIÓN POR CONSTRUCTOR:
     * Es la forma recomendada por Spring. Permite que el controlador sea
     * fácil de testear (Unit Testing) y asegura que todas las dependencias requeridas
     * estén presentes al instanciar la clase.
     */
    public ProfesorRestController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    /**
     * 1. OBTENER TODOS LOS PROFESORES
     * ResponseEntity: Clase de Spring que representa toda la respuesta HTTP (Status, Headers, Body).
     */
    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> obtenerTodos() {
        List<ProfesorDTO> profesores = profesorService.obtenerTodos();

        if (profesores.isEmpty()) {
            // CÓDIGO 204 (No Content): Indica éxito pero que la tabla está vacía.
            return ResponseEntity.noContent().build();
        }
        // CÓDIGO 200 (OK): Devuelve la lista de profesores en el cuerpo del JSON.
        return ResponseEntity.ok(profesores);
    }

    /**
     * 2. OBTENER POR ID
     * @PathVariable: Vincula la variable de la plantilla de la URL {id} con el parámetro del método.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> obtenerPorId(@PathVariable Long id) {
        ProfesorDTO profesor = profesorService.obtenerProfesorPorId(id);

        if (profesor == null) {
            // CÓDIGO 404 (Not Found): Respuesta cuando un ID no existe.
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profesor);
    }

    /**
     * 3. BUSCAR POR NOMBRE
     * Permite buscar profesores específicos (ej: /api/profesores/nombre/Severus).
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ProfesorDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<ProfesorDTO> profesores = profesorService.obtenerProfesorPorNombre(nombre);

        if (profesores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profesores);
    }
}