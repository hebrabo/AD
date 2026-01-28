package org.accesodatos.semana13dto.services;

import org.accesodatos.semana13dto.dtos.EstudianteDTO;

import java.util.List;

public interface EstudianteService {
    List<EstudianteDTO> obtenerTodos();

    EstudianteDTO obtenerEstudiantePorId(Long id);

    List<EstudianteDTO> obtenerEstudiantePorNombre(String nombre);
}
