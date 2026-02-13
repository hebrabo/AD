package org.accesodatos.semana15Delete.repositories;

import org.accesodatos.semana15Delete.models.EstudianteAsignatura;
import org.accesodatos.semana15Delete.models.EstudianteAsignaturaKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteAsignaturaRepository extends JpaRepository<EstudianteAsignatura, EstudianteAsignaturaKey> {
}