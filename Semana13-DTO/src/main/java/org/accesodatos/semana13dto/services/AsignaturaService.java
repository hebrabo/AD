package org.accesodatos.semana13dto.services;

import org.accesodatos.semana13dto.dtos.AsignaturaDTO;

import java.util.List;

public interface AsignaturaService {
    List<AsignaturaDTO> obtenerTodas();

    AsignaturaDTO obtenerAsignaturaPorId(Long id);

    List<AsignaturaDTO> obtenerAsignaturaPorNombre(String nombre);
}
