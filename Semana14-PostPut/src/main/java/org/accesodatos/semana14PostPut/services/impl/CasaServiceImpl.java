package org.accesodatos.semana14PostPut.services.impl;

import org.accesodatos.semana14PostPut.dtos.response.CasaDTO;
import org.accesodatos.semana14PostPut.mappers.CasaMapper;
import org.accesodatos.semana14PostPut.repositories.CasaRepository;
import org.accesodatos.semana14PostPut.services.CasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasaServiceImpl implements CasaService {
    private final CasaRepository casaRepository;
    private final CasaMapper casaMapper;

    @Autowired
    public CasaServiceImpl(CasaRepository casaRepository, CasaMapper casaMapper) {
        this.casaRepository = casaRepository;
        this.casaMapper = casaMapper;
    }

    @Override
    public List<CasaDTO> obtenerTodas() {
        return casaRepository.findAll().stream()
                .map(casaMapper::toDto)
                .toList();
    }

    @Override
    public CasaDTO obtenerCasaPorId(Long id) {
        return casaRepository.findById(id)
                .map(casaMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<CasaDTO> obtenerCasaPorNombre(String nombre) {
        return casaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(casaMapper::toDto)
                .toList();
    }
}
