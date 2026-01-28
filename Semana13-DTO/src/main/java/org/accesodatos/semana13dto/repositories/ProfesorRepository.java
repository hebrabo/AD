package org.accesodatos.semana13dto.repositories;

import org.accesodatos.semana13dto.models.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNombreContainingIgnoreCase(String nombre);
}
