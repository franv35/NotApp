package com.imb2025.notapp.service;

import java.util.List;

import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.entity.NoteTerminada;
import com.imb2025.notapp.entity.dto.RegisterRequest;

public interface INotesService {

    Note findById(Long id);

    List<Note> findAll();

    String createNote(RegisterRequest request);

    String deleteById(Long id);

    String updateTitle(Long Id, String title);


    String updateContent(Long id, String content);

    String updateNote (Long id, String title, String content);
    List<NoteTerminada>findAllTerminadas();

    String notaTerminada(Long id);
}