package org.accesodatos.semana14PostPut.services;

import org.accesodatos.semana14PostPut.dtos.response.AsignaturaDTO;

import java.util.List;

public interface AsignaturaService {
    List<AsignaturaDTO> obtenerTodas();

    AsignaturaDTO obtenerAsignaturaPorId(Long id);

    List<AsignaturaDTO> obtenerAsignaturaPorNombre(String nombre);
}
