package org.accesodatos.semana17Testing.services;

import org.accesodatos.semana17Testing.dtos.response.ProfesorDTO;
import java.util.List;

/**
 * INTERFAZ DE SERVICIO: PROFESOR
 * -----------------------------
 * Define el contrato de servicios para los profesores.
 * Esta interfaz asegura que el controlador solo dependa de abstracciones,
 * cumpliendo con el principio de Inversión de Dependencias.
 */
public interface ProfesorService {

    /**
     * Se utiliza ProfesorDTO como tipo de retorno.
     * El objetivo es desacoplar la API de la estructura interna de las tablas
     * de la base de datos, proporcionando una vista limpia y segura.
     */
    List<ProfesorDTO> obtenerTodos();

    /**
     * Operación de búsqueda única.
     * Permite recuperar el perfil de un docente específico mediante su ID.
     */
    ProfesorDTO obtenerProfesorPorId(Long id);

    /**
     * Consulta por criterio de búsqueda.
     * Facilita la localización de profesores por su nombre (ej: "Minerva" o "Severus").
     */
    List<ProfesorDTO> obtenerProfesorPorNombre(String nombre);
}