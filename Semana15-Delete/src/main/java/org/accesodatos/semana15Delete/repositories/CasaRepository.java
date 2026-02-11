package org.accesodatos.semana15Delete.repositories;

import org.accesodatos.semana15Delete.models.Casa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CasaRepository extends JpaRepository<Casa, Long> {
    List<Casa> findByNombreContainingIgnoreCase(String nombre);

}
