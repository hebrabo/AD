package org.accesodatos.semana14PostPut.repositories;

import org.accesodatos.semana14PostPut.models.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNombreContainingIgnoreCase(String nombre);
}
