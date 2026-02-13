package org.accesodatos.semana17Testing.repositories;

import org.accesodatos.semana17Testing.models.EstudianteAsignatura;
import org.accesodatos.semana17Testing.models.EstudianteAsignaturaKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteAsignaturaRepository extends JpaRepository<EstudianteAsignatura, EstudianteAsignaturaKey> {
}