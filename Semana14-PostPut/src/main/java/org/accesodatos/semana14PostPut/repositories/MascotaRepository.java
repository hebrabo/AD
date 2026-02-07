package org.accesodatos.semana14PostPut.repositories;

import org.accesodatos.semana14PostPut.models.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByNombreContainingIgnoreCase(String nombre);
}
