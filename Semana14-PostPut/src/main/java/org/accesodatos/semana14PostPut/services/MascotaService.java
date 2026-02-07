package org.accesodatos.semana14PostPut.services;

import org.accesodatos.semana14PostPut.dtos.response.MascotaDTO;

import java.util.List;

public interface MascotaService {
    List<MascotaDTO> obtenerTodas();

    MascotaDTO obtenerMascotaPorId(Long id);

    List<MascotaDTO> obtenerMascotasPorNombre(String nombre);
}
