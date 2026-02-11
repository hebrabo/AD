package org.accesodatos.semana15Delete.repositories;

import org.accesodatos.semana15Delete.models.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNombreContainingIgnoreCase(String nombre);
}
