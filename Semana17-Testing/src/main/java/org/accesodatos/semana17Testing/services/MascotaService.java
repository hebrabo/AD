package org.accesodatos.semana17Testing.services;

import org.accesodatos.semana17Testing.dtos.response.MascotaDTO;
import java.util.List;

/**
 * INTERFAZ DE SERVICIO: MASCOTA
 * ----------------------------
 * Define las operaciones permitidas para el recurso Mascota.
 * Actúa como un contrato: cualquier clase que implemente esta interfaz está
 * obligada a proporcionar estos métodos de consulta.
 */
public interface MascotaService {

    /**
     * Trabajamos con MascotaDTO para evitar la recursividad infinita.
     * Como las mascotas están ligadas a estudiantes, devolver la Entidad JPA
     * directamente causaría problemas al serializar a JSON.
     */
    List<MascotaDTO> obtenerTodas();

    /**
     * Búsqueda por ID único.
     * Es la operación base para obtener el detalle de una mascota específica.
     */
    MascotaDTO obtenerMascotaPorId(Long id);

    /**
     * Búsqueda por coincidencia de texto.
     * Permite al usuario buscar por nombres como "Hedwig" o "Scabbers".
     */
    List<MascotaDTO> obtenerMascotasPorNombre(String nombre);
}