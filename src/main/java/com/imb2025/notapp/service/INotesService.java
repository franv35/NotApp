package com.imb2025.notapp.service;

import java.util.List;

import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.entity.dto.NoteResponseDTO;
import com.imb2025.notapp.entity.dto.RegisterRequest;
import com.imb2025.notapp.enums.EstadoNota;

public interface INotesService {

    // ✅ Operaciones básicas
    Note findByIdAndUsuario(Long id, String username);
    List<Note> findAllByUsuario(String username);
    Note createNote(RegisterRequest request, String username);
    void deleteByIdAndUsuario(Long id, String username);
    Note updateTitle(Long id, String title, String username);
    Note updateContent(Long id, String content, String username);
    Note updateNote(Long id, String title, String content, String username);

    // ✅ Filtrado por estado
    List<Note> findByEstadoAndUsuario(EstadoNota estado, String username);

    // ✅ Asignaciones
    Note agregarEtiquetaANota(Long noteId, String nombreEtiqueta, String username);
    Note agregarColaboradorANota(Long noteId, Long colaboradorId, String username);
    Note agregarRecursoANota(Long noteId, Long recursoId, String username);
}
