package org.accesodatos.hogwarts.controller;

import org.accesodatos.hogwarts.model.Estudiante;
import org.accesodatos.hogwarts.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAPA DE CONTROLADOR (API REST)
 * ------------------------------
 * Esta clase se encarga de recibir las peticiones HTTP (GET, POST, DELETE...)
 * que vienen de internet/Postman.
 * Su trabajo es:
 * 1. Recibir la petición.
 * 2. Pedirle al Servicio que haga el trabajo.
 * 3. Devolver la respuesta en formato JSON con un código de estado (200, 404, 201...).
 */
@RestController // IMPORTANTE: Indica que esta clase devuelve DATOS (JSON), no vistas HTML.
@RequestMapping("/api/estudiantes") // LA PUERTA DE ENTRADA: Define la URL base.
// Todas las peticiones a esta clase deben empezar por: http://localhost:8080/api/estudiantes
public class EstudianteController {

    private final EstudianteService estudianteService;

    // INYECCIÓN DEL SERVICIO
    // El controlador necesita al servicio para procesar la lógica.
    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    /**
     * OBTENER TODOS LOS ESTUDIANTES
     * URL: GET http://localhost:8080/api/estudiantes
     */
    @GetMapping // Atiende peticiones tipo GET sin parámetros extra
    public ResponseEntity<List<Estudiante>> listarEstudiantes() {
        // ResponseEntity: Es un envoltorio que nos permite controlar el CÓDIGO DE ESTADO HTTP.
        // Aquí devolvemos la lista de alumnos y un 'HttpStatus.OK' (Código 200 - Todo bien).
        return new ResponseEntity<>(estudianteService.obtenerTodos(), HttpStatus.OK);
    }

    /**
     * OBTENER UN ESTUDIANTE POR SU ID
     * URL: GET http://localhost:8080/api/estudiantes/5
     */
    @GetMapping("/{id}") // El {id} es una variable dinámica en la URL (ej: 1, 5, 20)
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable Long id) {
        // @PathVariable: Extrae el número de la URL y lo mete en la variable 'id'.

        return estudianteService.obtenerPorId(id)
                .map(estudiante -> new ResponseEntity<>(estudiante, HttpStatus.OK)) // Si existe, devuelve 200 OK y el estudiante.
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si NO existe (Optional vacío), devuelve error 404.
    }

    /**
     * CREAR UN NUEVO ESTUDIANTE
     * URL: POST http://localhost:8080/api/estudiantes
     * BODY: { "nombre": "Luna", ... }
     */
    @PostMapping // Atiende peticiones tipo POST (para enviar datos nuevos)
    public ResponseEntity<Estudiante> crearEstudiante(@RequestBody Estudiante estudiante) {
        // @RequestBody: IMPORTANTE. Coge el JSON que envíamos en Postman y lo convierte
        // a un objeto Java 'Estudiante'.

        // Devolvemos HttpStatus.CREATED (Código 201) cuando se crea algo nuevo.
        return new ResponseEntity<>(estudianteService.guardarEstudiante(estudiante), HttpStatus.CREATED);
    }

    /**
     * ELIMINAR UN ESTUDIANTE
     * URL: DELETE http://localhost:8080/api/estudiantes/5
     */
    @DeleteMapping("/{id}") // Atiende peticiones tipo DELETE
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);

        // Devolvemos HttpStatus.NO_CONTENT (Código 204).
        // Significa "Lo he borrado bien, así que no tengo nada que mostrarte de vuelta".
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
