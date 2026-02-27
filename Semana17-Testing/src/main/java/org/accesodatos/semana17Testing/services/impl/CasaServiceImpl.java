package org.accesodatos.semana17Testing.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.accesodatos.semana17Testing.dtos.response.CasaDTO;
import org.accesodatos.semana17Testing.mappers.CasaMapper;
import org.accesodatos.semana17Testing.models.Casa;
import org.accesodatos.semana17Testing.models.Estudiante;
import org.accesodatos.semana17Testing.repositories.CasaRepository;
import org.accesodatos.semana17Testing.services.CasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE CASAS
 * ------------------------------------
 * Esta clase coordina el acceso a datos y la transformación.
 */

@Service
public class CasaServiceImpl implements CasaService {
    private final CasaRepository casaRepository;
    private final CasaMapper casaMapper;

    @Autowired
    public CasaServiceImpl(CasaRepository casaRepository, CasaMapper casaMapper) {
        this.casaRepository = casaRepository;
        this.casaMapper = casaMapper;
    }

    @Override
    public List<CasaDTO> obtenerTodas() {
        return casaRepository.findAll().stream()
                .map(casaMapper::toDto) // Convertimos cada Entidad en DTO
                .toList();
    }

    @Override
    public CasaDTO obtenerCasaPorId(Long id) {
        return casaRepository.findById(id)
                .map(casaMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<CasaDTO> obtenerCasaPorNombre(String nombre) {
        return casaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(casaMapper::toDto)
                .toList();
    }

    /**
     * BORRADO CON ESTRATEGIA SET NULL
     * -------------------------------
     * Al no usar CascadeType.REMOVE, si intentamos borrar una casa
     * que tiene alumnos, PostgreSQL daría un error de Foreign Key.
     * Esta lógica "rompe" los vínculos antes del borrado.
     */
    @Override
    @Transactional // OBLIGATORIO: Si el borrado falla, los estudiantes recuperan su casa (Rollback).
    public void borrarCasa(Long id) {
        // 1. Buscamos la entidad en el contexto de persistencia de Hibernate
        Casa casa = casaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Casa no encontrada"));

        // 2. DESVINCULACIÓN MANUAL (Para mantener integridad referencial)

        // A. Relación 1:N con Estudiantes:
        // Recorremos los estudiantes y les ponemos su casa a NULL.
        // No los borramos, solo los dejamos "sin casa".
        if (casa.getEstudiantes() != null) {
            for (Estudiante estudiante : casa.getEstudiantes()) {
                estudiante.setCasa(null);
            }
            casa.getEstudiantes().clear();
        }

        // B. Relación 1:1 con Profesor (Jefe de Casa):
        // Quitamos la referencia en ambos lados para evitar errores de FK.
        if (casa.getJefeCasa() != null) {
            casa.getJefeCasa().setCasa(null);
            casa.setJefeCasa(null);
        }

        // 3. Una vez que la casa está "aislada" y no tiene hijos apuntando a ella, borramos.
        casaRepository.delete(casa);
    }
}