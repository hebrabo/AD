package org.accesodatos.semana15Delete.services;

import org.accesodatos.semana15Delete.dtos.response.MascotaDTO;

import java.util.List;

public interface MascotaService {
    List<MascotaDTO> obtenerTodas();

    MascotaDTO obtenerMascotaPorId(Long id);

    List<MascotaDTO> obtenerMascotasPorNombre(String nombre);
}
