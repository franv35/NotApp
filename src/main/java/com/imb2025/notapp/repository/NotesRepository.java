package com.imb2025.notapp.repository;

import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.enums.EstadoNota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {

    // Buscar por título (ya lo tenías)
    Optional<Note> findByTitle(String title);

    // ✅ Buscar todas las notas de un usuario
    List<Note> findByUsuarioUsername(String username);

    // ✅ Buscar nota por id y usuario (validar propiedad)
    Optional<Note> findByIdAndUsuarioUsername(Long id, String username);

    // ✅ Buscar notas por estado y usuario
    List<Note> findByEstadoAndUsuarioUsername(EstadoNota estado, String username);
}
