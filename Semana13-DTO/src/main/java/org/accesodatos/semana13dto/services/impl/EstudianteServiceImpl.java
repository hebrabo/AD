package org.accesodatos.semana13dto.services.impl;

import org.accesodatos.semana13dto.dtos.EstudianteDTO;
import org.accesodatos.semana13dto.mappers.EstudianteMapper;
import org.accesodatos.semana13dto.repositories.EstudianteRepository;
import org.accesodatos.semana13dto.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteServiceImpl implements EstudianteService {
    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper estudianteMapper;

    @Autowired
    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, EstudianteMapper estudianteMapper) {
        this.estudianteRepository = estudianteRepository;
        this.estudianteMapper = estudianteMapper;
    }

    @Override
    public List<EstudianteDTO> obtenerTodos() {
        return estudianteRepository.findAll().stream()
                .map(estudianteMapper::toDto)
                .toList();
    }

    @Override
    public EstudianteDTO obtenerEstudiantePorId(Long id) {
        return estudianteRepository.findById(id)
                .map(estudianteMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<EstudianteDTO> obtenerEstudiantePorNombre(String nombre) {
        return estudianteRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(estudianteMapper::toDto)
                .toList();
    }
}
