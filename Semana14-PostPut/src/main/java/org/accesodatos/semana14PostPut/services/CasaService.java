package org.accesodatos.semana14PostPut.services;

import org.accesodatos.semana14PostPut.dtos.response.CasaDTO;

import java.util.List;

public interface CasaService {
    List<CasaDTO> obtenerTodas();

    CasaDTO obtenerCasaPorId(Long id);

    List<CasaDTO> obtenerCasaPorNombre(String nombre);
}
