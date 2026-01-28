package org.accesodatos.semana13dto.services.impl;

import org.accesodatos.semana13dto.dtos.ProfesorDTO;
import org.accesodatos.semana13dto.mappers.ProfesorMapper;
import org.accesodatos.semana13dto.repositories.ProfesorRepository;
import org.accesodatos.semana13dto.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    private final ProfesorRepository profesorRepository;
    private final ProfesorMapper profesorMapper;

    @Autowired
    public ProfesorServiceImpl(ProfesorRepository profesorRepository, ProfesorMapper profesorMapper) {
        this.profesorRepository = profesorRepository;
        this.profesorMapper = profesorMapper;
    }

    @Override
    public List<ProfesorDTO> obtenerTodos() {
        return profesorRepository.findAll().stream()
                .map(profesorMapper::toDto)
                .toList();
    }

    @Override
    public ProfesorDTO obtenerProfesorPorId(Long id) {
        return profesorRepository.findById(id)
                .map(profesorMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<ProfesorDTO> obtenerProfesorPorNombre(String nombre) {
        return profesorRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(profesorMapper::toDto)
                .toList();
    }
}
