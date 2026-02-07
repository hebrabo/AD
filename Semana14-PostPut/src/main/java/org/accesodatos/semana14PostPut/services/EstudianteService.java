package org.accesodatos.semana14PostPut.services;

import org.accesodatos.semana14PostPut.dtos.request.create.EstudianteCreateDTO;
import org.accesodatos.semana14PostPut.dtos.request.update.EstudianteUpdateDTO;
import org.accesodatos.semana14PostPut.dtos.response.EstudianteDTO;

import java.util.List;

public interface EstudianteService {

    List<EstudianteDTO> obtenerTodos();

    EstudianteDTO obtenerEstudiantePorId(Long id);

    List<EstudianteDTO> obtenerEstudiantePorNombre(String nombre);

    // --- MÉTODOS POST y PUT ---
    EstudianteDTO crearEstudiante(EstudianteCreateDTO dto);

    EstudianteDTO actualizarEstudiante(Long id, EstudianteUpdateDTO dto);
}