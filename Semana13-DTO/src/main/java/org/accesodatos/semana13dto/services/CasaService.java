package org.accesodatos.semana13dto.services;

import org.accesodatos.semana13dto.dtos.CasaDTO;

import java.util.List;

public interface CasaService {
    List<CasaDTO> obtenerTodas();

    CasaDTO obtenerCasaPorId(Long id);

    List<CasaDTO> obtenerCasaPorNombre(String nombre);
}
