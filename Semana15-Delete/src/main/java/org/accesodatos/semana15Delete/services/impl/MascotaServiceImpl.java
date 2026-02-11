package org.accesodatos.semana15Delete.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana15Delete.dtos.response.MascotaDTO;
import org.accesodatos.semana15Delete.mappers.MascotaMapper;
import org.accesodatos.semana15Delete.models.Mascota;
import org.accesodatos.semana15Delete.repositories.MascotaRepository;
import org.accesodatos.semana15Delete.services.MascotaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final MascotaMapper mascotaMapper;

    @Override
    public List<MascotaDTO> obtenerTodas() {
        return mascotaRepository.findAll().stream()
                .map(mascotaMapper::toDto)
                .toList();
    }

    @Override
    public MascotaDTO obtenerMascotaPorId(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada con id: " + id));

        return mascotaMapper.toDto(mascota);
    }

    @Override
    public List<MascotaDTO> obtenerMascotasPorNombre(String nombre) {
        return mascotaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(mascotaMapper::toDto)
                .toList();
    }
}