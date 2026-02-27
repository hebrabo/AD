package org.accesodatos.semana17Testing.services;

import org.accesodatos.semana17Testing.dtos.response.AsignaturaDTO;
import java.util.List;

/**
 * CAPA DE SERVICIO (INTERFAZ)
 * ---------------------------
 * Esta interfaz define la LÓGICA DE NEGOCIO.
 * Actúa como una fachada (Pattern Facade) para que el Controlador no tenga que
 * saber cómo se buscan los datos o cómo se mapean; solo pide lo que necesita.
 */

public interface AsignaturaService {

    /**
     * EXPLICACIÓN: Trabajamos exclusivamente con DTOs.
     * Nunca devolvemos Entidades directamente al Controlador para mantener
     * la arquitectura limpia y segura.
     */
    List<AsignaturaDTO> obtenerTodas();

    AsignaturaDTO obtenerAsignaturaPorId(Long id);

    List<AsignaturaDTO> obtenerAsignaturaPorNombre(String nombre);

    /**
     * ESTRATEGIA DE BORRADO: RESTRICT
     * ------------------------------
     * A diferencia de los estudiantes (donde usamos cascada),
     * en las asignaturas la lógica suele ser restrictiva. No deberíamos poder
     * borrar una asignatura si hay alumnos matriculados o un profesor asignado
     * sin gestionar primero esas relaciones.
     */
    void borrarAsignatura(Long id);
}