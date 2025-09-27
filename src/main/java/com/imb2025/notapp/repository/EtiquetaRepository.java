package com.imb2025.notapp.repository;

import com.imb2025.notapp.entity.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {
    Optional<Etiqueta> findByNombre(String nombre);
}

