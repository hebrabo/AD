package org.accesodatos.semana13dto.repositories;

import org.accesodatos.semana13dto.models.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    List<Asignatura> findByNombreContainingIgnoreCase(String nombre);
}
