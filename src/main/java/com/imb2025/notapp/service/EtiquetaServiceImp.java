package com.imb2025.notapp.service;

import com.imb2025.notapp.entity.Etiqueta;
import com.imb2025.notapp.repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EtiquetaServiceImp implements EtiquetaService {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Override
    public Optional<Etiqueta> findByNombre(String nombre) {
        return etiquetaRepository.findByNombre(nombre);
    }

    @Override
    public Etiqueta save(Etiqueta etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }
}
