package org.accesodatos.semana17Testing.services;

import org.accesodatos.semana17Testing.dtos.response.ProfesorDTO;

import java.util.List;

public interface ProfesorService {
    List<ProfesorDTO> obtenerTodos();

    ProfesorDTO obtenerProfesorPorId(Long id);

    List<ProfesorDTO> obtenerProfesorPorNombre(String nombre);
}
