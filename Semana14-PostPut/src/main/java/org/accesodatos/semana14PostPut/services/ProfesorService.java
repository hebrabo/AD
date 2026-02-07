package org.accesodatos.semana14PostPut.services;

import org.accesodatos.semana14PostPut.dtos.response.ProfesorDTO;

import java.util.List;

public interface ProfesorService {
    List<ProfesorDTO> obtenerTodos();

    ProfesorDTO obtenerProfesorPorId(Long id);

    List<ProfesorDTO> obtenerProfesorPorNombre(String nombre);
}
