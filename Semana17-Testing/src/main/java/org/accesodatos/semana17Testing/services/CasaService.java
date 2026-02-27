package org.accesodatos.semana17Testing.services;

import org.accesodatos.semana17Testing.dtos.response.CasaDTO;
import java.util.List;

/**
 * CAPA DE SERVICIO: INTERFAZ CASA
 * ------------------------------
 * Define los servicios disponibles para la gestión de las
 * Casas de Hogwarts. Al ser una interfaz, desacopla la definición de la lógica
 * de su implementación real (Principio de Inversión de Dependencias).
 */
public interface CasaService {

    /**
     * Se trabaja siempre con CasaDTO.
     * Esto asegura que la capa de presentación (API) nunca manipule
     * directamente las entidades de la base de datos.
     */
    List<CasaDTO> obtenerTodas();

    CasaDTO obtenerCasaPorId(Long id);

    List<CasaDTO> obtenerCasaPorNombre(String nombre);

    /**
     * ESTRATEGIA DE BORRADO: SET NULL
     * ------------------------------
     * A diferencia de los estudiantes (donde usamos borrado
     * en cascada), para las Casas aplicamos una lógica de "Set Null".
     * Si borramos una Casa (ej. Slytherin), no queremos borrar a sus alumnos;
     * simplemente queremos que esos alumnos se queden temporalmente "sin casa".
     */
    void borrarCasa(Long id);
}