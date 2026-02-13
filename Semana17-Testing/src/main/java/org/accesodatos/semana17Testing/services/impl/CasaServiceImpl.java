package org.accesodatos.semana17Testing.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.accesodatos.semana17Testing.dtos.response.CasaDTO;
import org.accesodatos.semana17Testing.mappers.CasaMapper;
import org.accesodatos.semana17Testing.models.Casa;
import org.accesodatos.semana17Testing.models.Estudiante;
import org.accesodatos.semana17Testing.repositories.CasaRepository;
import org.accesodatos.semana17Testing.services.CasaService;
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

    // --- DELETE (SET NULL)  ---
    @Override
    @Transactional
    public void borrarCasa(Long id) {
        // 1. Buscamos la casa
        Casa casa = casaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Casa no encontrada"));

        // 2. DESVINCULAR DE UN LADO Y DE OTRO

        // A. Desvincular Estudiantes (Lado Many)
        if (casa.getEstudiantes() != null) {
            for (Estudiante estudiante : casa.getEstudiantes()) {
                estudiante.setCasa(null); // El estudiante ya no apunta a la casa
            }
            casa.getEstudiantes().clear(); // La casa ya no apunta a los estudiantes
        }

        // B. Desvincular Jefe de Casa (Lado OneToOne)
        if (casa.getJefeCasa() != null) {
            casa.getJefeCasa().setCasa(null);
            casa.setJefeCasa(null);
        }

        // 3. EJECUTAR EL BORRADO
        casaRepository.delete(casa);
    }
}
