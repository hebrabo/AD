package org.accesodatos.semana14PostPut.repositories;

import org.accesodatos.semana14PostPut.models.Casa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CasaRepository extends JpaRepository<Casa, Long> {
    List<Casa> findByNombreContainingIgnoreCase(String nombre);

}
