package org.accesodatos.semana14PostPut.repositories;

import org.accesodatos.semana14PostPut.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByNombreContainingIgnoreCase(String nombre);
}
