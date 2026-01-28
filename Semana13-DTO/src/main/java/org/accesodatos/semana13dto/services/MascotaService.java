package org.accesodatos.semana13dto.services;

import org.accesodatos.semana13dto.dtos.MascotaDTO;

import java.util.List;

public interface MascotaService {
    List<MascotaDTO> obtenerTodas();

    MascotaDTO obtenerMascotaPorId(Long id);

    List<MascotaDTO> obtenerMascotasPorNombre(String nombre);
}
