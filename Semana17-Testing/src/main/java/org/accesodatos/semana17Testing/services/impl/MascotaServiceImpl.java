package org.accesodatos.semana17Testing.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.accesodatos.semana17Testing.dtos.response.MascotaDTO;
import org.accesodatos.semana17Testing.mappers.MascotaMapper;
import org.accesodatos.semana17Testing.models.Mascota;
import org.accesodatos.semana17Testing.repositories.MascotaRepository;
import org.accesodatos.semana17Testing.services.MascotaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE MASCOTAS
 * --------------------------------------
 * Esta clase implementa la lógica de negocio para las mascotas.
 * Su función principal es servir de puente entre el repositorio (datos crudos)
 * y el mapper (datos procesados para el cliente).
 */

@Service // Marca la clase como componente de servicio para el escaneo de beans de Spring.
@RequiredArgsConstructor // LOMBOK: Realiza la inyección de dependencias por constructor de los campos 'final'.
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final MascotaMapper mascotaMapper;

    /**
     * OBTENER TODAS LAS MASCOTAS
     * Utilizamos Java Streams. Es más eficiente y moderno que un bucle for.
     * 1. Obtenemos la lista de entidades.
     * 2. 'map': Transforma cada Mascota en MascotaDTO usando el mapper.
     * 3. 'toList': Agrupa el resultado en la lista final.
     */
    @Override
    public List<MascotaDTO> obtenerTodas() {
        return mascotaRepository.findAll().stream()
                .map(mascotaMapper::toDto)
                .toList();
    }

    /**
     * OBTENER POR ID
     * Aquí aplicamos el patrón 'Fail-Fast'.
     * 'orElseThrow': Si el Optional de findById está vacío, lanzamos la excepción inmediatamente.
     * Esto evita que el mapper reciba un nulo y falle después.
     */
    @Override
    public MascotaDTO obtenerMascotaPorId(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada con id: " + id));

        return mascotaMapper.toDto(mascota);
    }

    /**
     * BUSCAR POR NOMBRE
     * Delega en el Query Method del repositorio para realizar
     * una búsqueda flexible (LIKE) en la base de datos.
     */
    @Override
    public List<MascotaDTO> obtenerMascotasPorNombre(String nombre) {
        return mascotaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(mascotaMapper::toDto)
                .toList();
    }
}