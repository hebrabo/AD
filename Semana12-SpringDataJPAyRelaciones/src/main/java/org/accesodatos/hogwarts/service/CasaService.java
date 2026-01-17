package org.accesodatos.hogwarts.service;

import org.accesodatos.hogwarts.model.Casa;
import org.accesodatos.hogwarts.repository.CasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CasaService {

    @Autowired
    private CasaRepository casaRepository;

    public List<Casa> obtenerTodas() {
        return casaRepository.findAll();
    }
}