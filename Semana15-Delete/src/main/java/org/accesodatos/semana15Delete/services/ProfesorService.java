package org.accesodatos.semana15Delete.services;

import org.accesodatos.semana15Delete.dtos.response.ProfesorDTO;

import java.util.List;

public interface ProfesorService {
    List<ProfesorDTO> obtenerTodos();

    ProfesorDTO obtenerProfesorPorId(Long id);

    List<ProfesorDTO> obtenerProfesorPorNombre(String nombre);
}
