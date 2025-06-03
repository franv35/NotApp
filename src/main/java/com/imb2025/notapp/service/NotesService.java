package com.imb2025.notapp.service;

import java.util.List;

import com.imb2025.notapp.entity.Note;

public interface INotesService {

    Notes findById(Long id);

    List<Note> findAll();

    Notes save(Notes estadoCursada);

    Notes update(Long id, Notes estadoCursada);

    void deleteById(Long id);

}