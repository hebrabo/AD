package org.accesodatos.semana13dto.services;

import org.accesodatos.semana13dto.dtos.MascotaDTO;
import org.accesodatos.semana13dto.dtos.ProfesorDTO;

import java.util.List;

public interface ProfesorService {
    List<ProfesorDTO> obtenerTodos();

    ProfesorDTO obtenerProfesorPorId(Long id);

    List<ProfesorDTO> obtenerProfesorPorNombre(String nombre);
}
