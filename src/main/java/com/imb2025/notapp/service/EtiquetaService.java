package com.imb2025.notapp.service;

import com.imb2025.notapp.entity.Etiqueta;

import java.util.Optional;

public interface EtiquetaService {
    Optional<Etiqueta> findByNombre(String nombre);
    Etiqueta save(Etiqueta etiqueta);
}
