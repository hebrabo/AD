package org.accesodatos.semana17Testing.repositories;

import org.accesodatos.semana17Testing.models.Casa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CasaRepository extends JpaRepository<Casa, Long> {
    List<Casa> findByNombreContainingIgnoreCase(String nombre);

}
