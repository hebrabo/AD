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

    // --- DELETE (RESTRICT) ---
    @Override
    @Transactional
    public void borrarAsignatura(Long id) {
        // 1. Buscamos la asignatura o lanzamos error si no existe
        Asignatura asignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La asignatura con ID " + id + " no existe."));

        // 2. RESTRICT: Comprobamos la tabla intermedia de CALIFICACIONES
        if (asignatura.getCalificaciones() != null && !asignatura.getCalificaciones().isEmpty()) {
            throw new IllegalStateException("RESTRICT: No se puede borrar '" + asignatura.getNombre() +
                    "' porque tiene estudiantes con calificaciones registradas.");
        }

        // 3. SET NULL: Desvincular al Profesor asociado
        if (asignatura.getProfesor() != null) {
            asignatura.getProfesor().setAsignatura(null);
            asignatura.setProfesor(null);
        }

        // 4. Borrado final
        asignaturaRepository.delete(asignatura);
    }
}
