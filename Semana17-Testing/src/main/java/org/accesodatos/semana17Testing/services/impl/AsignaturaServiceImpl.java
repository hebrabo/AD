package org.accesodatos.semana17Testing.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.accesodatos.semana17Testing.dtos.response.AsignaturaDTO;
import org.accesodatos.semana17Testing.mappers.AsignaturaMapper;
import org.accesodatos.semana17Testing.models.Asignatura;
import org.accesodatos.semana17Testing.repositories.AsignaturaRepository;
import org.accesodatos.semana17Testing.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IMPLEMENTACIÓN DE LÓGICA DE NEGOCIO
 * ----------------------------------
 * Esta clase es el "músculo" de la aplicación. Aquí es donde
 * se orquesta el uso del Repositorio (para BD) y el Mapper (para la API).
 */

@Service // Indica a Spring que esta clase contiene la lógica de negocio y debe ser un Bean.
public class AsignaturaServiceImpl implements AsignaturaService {

    // Inyección de dependencias: usamos 'final' para garantizar que no cambien.
    private final AsignaturaRepository asignaturaRepository;
    private final AsignaturaMapper asignaturaMapper;

    @Autowired
    public AsignaturaServiceImpl(AsignaturaRepository asignaturaRepository, AsignaturaMapper asignaturaMapper) {
        this.asignaturaRepository = asignaturaRepository;
        this.asignaturaMapper = asignaturaMapper;
    }

    @Override
    public List<AsignaturaDTO> obtenerTodas() {
        // Programación Funcional: Obtenemos entidades -> Mapeamos a DTO -> Convertimos a Lista.
        return asignaturaRepository.findAll().stream()
                .map(asignaturaMapper::toDto)
                .toList();
    }

    @Override
    public AsignaturaDTO obtenerAsignaturaPorId(Long id) {
        // Uso de Optional: Si existe lo mapea, si no devuelve null.
        // El controlador se encargará de lanzar el 404.
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

    /**
     * BORRADO CON LÓGICA DE RESTRICCIÓN (RESTRICT/SET NULL)
     * ----------------------------------------------------
     * No es un simple borrado. Aquí aplicamos reglas para
     * mantener la integridad de la base de datos de Hogwarts.
     */
    @Override
    @Transactional // IMPORTANTE: Garantiza que si algo falla, se haga un ROLLBACK y la BD no quede corrupta.
    public void borrarAsignatura(Long id) {
        // 1. CONTROL DE EXISTENCIA: Antes de borrar, verificamos que exista.
        Asignatura asignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La asignatura con ID " + id + " no existe."));

        // 2. REGLA 'RESTRICT' (Calificaciones):
        // Si hay notas puestas en la tabla intermedia, prohibimos el borrado para no perder el historial académico.
        if (asignatura.getCalificaciones() != null && !asignatura.getCalificaciones().isEmpty()) {
            throw new IllegalStateException("RESTRICT: No se puede borrar '" + asignatura.getNombre() +
                    "' porque tiene estudiantes con calificaciones registradas.");
        }

        // 3. REGLA 'SET NULL' (Profesor):
        // Si la asignatura tiene profesor, primero le quitamos la asignatura al profesor
        // para que la Foreign Key no impida el borrado (Integridad Referencial).
        if (asignatura.getProfesor() != null) {
            asignatura.getProfesor().setAsignatura(null);
            asignatura.setProfesor(null);
        }

        // 4. Ejecución del borrado en el repositorio.
        asignaturaRepository.delete(asignatura);
    }
}