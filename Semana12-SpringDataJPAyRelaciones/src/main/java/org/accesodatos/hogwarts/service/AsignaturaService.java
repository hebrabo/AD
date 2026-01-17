package org.accesodatos.hogwarts.service;

import org.accesodatos.hogwarts.model.Asignatura;
import org.accesodatos.hogwarts.repository.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public List<Asignatura> obtenerTodas() {
        return asignaturaRepository.findAll();
    }
}