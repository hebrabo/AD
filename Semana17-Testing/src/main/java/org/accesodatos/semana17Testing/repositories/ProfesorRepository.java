package org.accesodatos.semana17Testing.repositories;

import org.accesodatos.semana17Testing.models.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNombreContainingIgnoreCase(String nombre);
}
