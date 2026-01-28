package org.accesodatos.semana13dto.services.impl;

import org.accesodatos.semana13dto.dtos.MascotaDTO;
import org.accesodatos.semana13dto.mappers.MascotaMapper;
import org.accesodatos.semana13dto.repositories.MascotaRepository;
import org.accesodatos.semana13dto.services.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {
    private final MascotaRepository mascotaRepository;
    private final MascotaMapper mascotaMapper;

    @Autowired
    public MascotaServiceImpl(MascotaRepository mascotaRepository, MascotaMapper mascotaMapper) {
        this.mascotaRepository = mascotaRepository;
        this.mascotaMapper = mascotaMapper;
    }

    @Override
    public List<MascotaDTO> obtenerTodas() {
        return mascotaRepository.findAll().stream()
                .map(mascotaMapper::toDto)
                .toList();
    }

    @Override
    public MascotaDTO obtenerMascotaPorId(Long id) {
        return mascotaRepository.findById(id)
                .map(mascotaMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<MascotaDTO> obtenerMascotasPorNombre(String nombre) {
        return mascotaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(mascotaMapper::toDto)
                .toList();
    }
}
