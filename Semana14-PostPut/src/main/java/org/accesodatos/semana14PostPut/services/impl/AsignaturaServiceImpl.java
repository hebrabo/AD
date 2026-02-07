package org.accesodatos.semana14PostPut.services.impl;

import org.accesodatos.semana14PostPut.dtos.response.AsignaturaDTO;
import org.accesodatos.semana14PostPut.mappers.AsignaturaMapper;
import org.accesodatos.semana14PostPut.repositories.AsignaturaRepository;
import org.accesodatos.semana14PostPut.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {
    private final AsignaturaRepository asignaturaRepository;
    private final AsignaturaMapper asignaturaMapper;

    @Autowired
    public AsignaturaServiceImpl(AsignaturaRepository asignaturaRepository, AsignaturaMapper asignaturaMapper) {
        this.asignaturaRepository = asignaturaRepository;
        this.asignaturaMapper = asignaturaMapper;
    }

    @Override
    public List<AsignaturaDTO> obtenerTodas() {
        return asignaturaRepository.findAll().stream()
                .map(asignaturaMapper::toDto)
                .toList();
    }

    @Override
    public AsignaturaDTO obtenerAsignaturaPorId(Long id) {
        return asignaturaRepository.findById(id)
                .map(asignaturaMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<AsignaturaDTO> obtenerAsignaturaPorNombre(String nombre) {
        return asignaturaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(asignaturaMapper::toDto)
                .toList();
    }
}
