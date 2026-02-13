package org.accesodatos.semana17Testing.repositories;

import org.accesodatos.semana17Testing.models.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    List<Asignatura> findByNombreContainingIgnoreCase(String nombre);
}
