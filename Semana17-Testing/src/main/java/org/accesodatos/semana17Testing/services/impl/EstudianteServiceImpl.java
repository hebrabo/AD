package org.accesodatos.semana17Testing.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana17Testing.dtos.request.create.EstudianteCreateDTO;
import org.accesodatos.semana17Testing.dtos.request.update.EstudianteUpdateDTO;
import org.accesodatos.semana17Testing.dtos.response.EstudianteDTO;
import org.accesodatos.semana17Testing.mappers.EstudianteMapper;
import org.accesodatos.semana17Testing.models.Casa;
import org.accesodatos.semana17Testing.models.Estudiante;
import org.accesodatos.semana17Testing.repositories.CasaRepository;
import org.accesodatos.semana17Testing.repositories.EstudianteRepository;
import org.accesodatos.semana17Testing.services.EstudianteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE ESTUDIANTES
 * -----------------------------------------
 * Esta clase es el "cerebro" del CRUD de Hogwarts.
 * Combina varios repositorios y mappers para asegurar que el flujo de datos
 * sea coherente y cumpla las reglas de integridad de la base de datos.
 */

@Service
@RequiredArgsConstructor // LOMBOK: Genera el constructor para inyectar los 3 componentes finales.
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final CasaRepository casaRepository;
    private final EstudianteMapper estudianteMapper;

    @Override
    public List<EstudianteDTO> obtenerTodos() {
        return estudianteRepository.findAll()
                .stream()
                .map(estudianteMapper::toDto)
                .toList();
    }

    @Override
    public EstudianteDTO obtenerEstudiantePorId(Long id) {
        // CONTROL DE ERRORES: Si no existe, lanzamos excepción. Spring la convertirá en un error 404.
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con id: " + id));
        return estudianteMapper.toDto(estudiante);
    }

    @Override
    public List<EstudianteDTO> obtenerEstudiantePorNombre(String nombre) {
        return estudianteRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(estudianteMapper::toDto)
                .toList();
    }

    // --- MÉTODOS POST Y PUT ---

    /**
     * CREAR ESTUDIANTE (POST)
     * @Transactional: Si la casa no existe o el guardado falla, no se crea nada (Atomicidad).
     */
    @Override
    @Transactional
    public EstudianteDTO crearEstudiante(EstudianteCreateDTO dto) {
        // 1. Convertimos DTO a Entidad
        Estudiante estudiante = estudianteMapper.toEntity(dto);

        // 2. REGLA DE NEGOCIO: Asignar Casa por ID si viene en el DTO
        if (dto.getCasaId() != null) {
            Casa casa = casaRepository.findById(dto.getCasaId())
                    .orElseThrow(() -> new EntityNotFoundException("Casa no encontrada con id: " + dto.getCasaId()));
            estudiante.setCasa(casa); // Vinculamos la FK antes de guardar
        }

        Estudiante estudianteGuardado = estudianteRepository.save(estudiante);
        return estudianteMapper.toDto(estudianteGuardado);
    }

    /**
     * ACTUALIZAR ESTUDIANTE (PUT)
     * No creamos un objeto nuevo. Recuperamos el existente
     * y el Mapper actualiza sus campos.
     */
    @Override
    @Transactional
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteUpdateDTO dto) {
        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con id: " + id));

        // El Mapper sincroniza los datos del DTO en la Entidad gestionada
        estudianteMapper.updateEstudianteFromDto(dto, estudianteExistente);

        return estudianteMapper.toDto(estudianteRepository.save(estudianteExistente));
    }

    // --- MÉTODO DELETE ---

    /**
     * BORRAR ESTUDIANTE (CON REGLA DE NEGOCIO)
     * Antes de borrar, comprobamos condiciones.
     * En Hogwarts no se puede expulsar a alguien si su mascota sigue allí.
     */
    @Override
    @Transactional
    public void borrarEstudiante(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con id: " + id));

        // REGLA DE PROTECCIÓN: Evita el borrado si hay una relación activa que lo impida.
        if (estudiante.getMascota() != null) {
            throw new IllegalStateException("No se puede expulsar a un estudiante con mascota activa.");
        }

        estudianteRepository.delete(estudiante);
    }
}