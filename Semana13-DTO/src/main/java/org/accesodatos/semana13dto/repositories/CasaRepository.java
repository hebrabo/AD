package org.accesodatos.semana13dto.repositories;

import org.accesodatos.semana13dto.models.Casa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CasaRepository extends JpaRepository<Casa, Long> {
    List<Casa> findByNombreContainingIgnoreCase(String nombre);

}
