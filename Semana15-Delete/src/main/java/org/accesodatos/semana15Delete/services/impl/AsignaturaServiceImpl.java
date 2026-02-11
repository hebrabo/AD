package org.accesodatos.semana15Delete.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.accesodatos.semana15Delete.dtos.response.AsignaturaDTO;
import org.accesodatos.semana15Delete.mappers.AsignaturaMapper;
import org.accesodatos.semana15Delete.models.Asignatura;
import org.accesodatos.semana15Delete.models.Profesor;
import org.accesodatos.semana15Delete.repositories.AsignaturaRepository;
import org.accesodatos.semana15Delete.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {
    private final AsignaturaRepository asignaturaRepository;
    private final AsignaturaMapper asignaturaMapper;

    @Autowired
    public AsignaturaServiceImpl(AsignaturaRepository asignaturaRepository, AsignaturaMapper asignaturaMapper) {
        this.asignaturaRepository = asignaturaRepository;
        this.asignaturaMapper = asignaturaMapper;
    }

    @Override
    public List<AsignaturaDTO> obtenerTodas() {
        return asignaturaRepository.findAll().stream()
                .map(asignaturaMapper::toDto)
                .toList();
    }

    @Override
    public AsignaturaDTO obtenerAsignaturaPorId(Long id) {
        return asignaturaRepository.findById(id)
                .map(asignaturaMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<AsignaturaDTO> obtenerAsignaturaPorNombre(String nombre) {
        return asignaturaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(asignaturaMapper::toDto)
                .toList();
    }

    // --- DELETE (RESTRICT)
    @Override
    @Transactional
    public void borrarAsignatura(Long id) {
        // 1. Buscamos la asignatura
        Asignatura asignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada"));

        // 2. RESTRICT: Si hay alumnos, bloqueamos
        if (asignatura.getEstudiantes() != null && !asignatura.getEstudiantes().isEmpty()) {
            throw new IllegalStateException("No se puede borrar: existen estudiantes matriculados.");
        }

        // 3. SET NULL: Desvincular al Profesor
        if (asignatura.getProfesor() != null) {
            // Desvinculamos de ambos lados
            asignatura.getProfesor().setAsignatura(null);
            asignatura.setProfesor(null);
        }

        // 4. Borrado
        asignaturaRepository.delete(asignatura);
    }
}
