package com.imb2025.notapp.service.jpa.NoteService;

import com.imb2025.notapp.entity.Note;

import java.util.List;


public interface INoteServiceProcessor {
   List<Note> findAll();

    Note findById(Long id);

    String deleteById(Long id);
    String updateTitle(String title);
}
