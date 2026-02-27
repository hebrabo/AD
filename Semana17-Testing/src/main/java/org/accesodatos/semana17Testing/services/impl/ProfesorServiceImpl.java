package org.accesodatos.semana17Testing.services.impl;

import org.accesodatos.semana17Testing.dtos.response.ProfesorDTO;
import org.accesodatos.semana17Testing.mappers.ProfesorMapper;
import org.accesodatos.semana17Testing.repositories.ProfesorRepository;
import org.accesodatos.semana17Testing.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IMPLEMENTACIÓN DEL SERVICIO: PROFESOR
 * ------------------------------------
 * Esta clase actúa como el orquestador.
 * Recupera datos de la base de datos (Entidades) y los entrega a la API
 * transformados (DTOs) para no exponer el modelo interno.
 */

@Service // Indica que es un componente de lógica de negocio gestionado por Spring.
public class ProfesorServiceImpl implements ProfesorService {

    // Atributos finales para asegurar la inmutabilidad de las dependencias.
    private final ProfesorRepository profesorRepository;
    private final ProfesorMapper profesorMapper;

    @Autowired // Inyección por constructor: La opción más segura y testeable.
    public ProfesorServiceImpl(ProfesorRepository profesorRepository, ProfesorMapper profesorMapper) {
        this.profesorRepository = profesorRepository;
        this.profesorMapper = profesorMapper;
    }

    /**
     * OBTENER TODOS LOS PROFESORES
     * Usamos Java Streams para transformar la lista de entidades
     * en una lista de DTOs de forma funcional y eficiente.
     */
    @Override
    public List<ProfesorDTO> obtenerTodos() {
        return profesorRepository.findAll().stream()
                .map(profesorMapper::toDto)
                .toList();
    }

    /**
     * OBTENER POR ID
     * El metodo 'findById' devuelve un Optional.
     * Si el profesor existe, se mapea; si no, devolvemos null (el controlador
     * gestionará el 404).
     */
    @Override
    public ProfesorDTO obtenerProfesorPorId(Long id) {
        return profesorRepository.findById(id)
                .map(profesorMapper::toDto)
                .orElse(null);
    }

    /**
     * BUSCAR POR NOMBRE
     * Delegamos la búsqueda en el Repositorio (Query Method)
     * y procesamos los resultados para devolver DTOs limpios.
     */
    @Override
    public List<ProfesorDTO> obtenerProfesorPorNombre(String nombre) {
        return profesorRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(profesorMapper::toDto)
                .toList();
    }
}