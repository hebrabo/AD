package org.accesodatos.semana14PostPut.repositories;

import org.accesodatos.semana14PostPut.models.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    List<Asignatura> findByNombreContainingIgnoreCase(String nombre);
}
