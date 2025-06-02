package com.imb2025.notapp.service;

import java.util.List;

import com.imb2025.notapp.entity.Notes;

public interface NotesService {

    Notes findById(Long id);

    List<Notes> findAll();

    Notes save(Notes estadoCursada);

    Notes update(Long id, Notes estadoCursada);

    void deleteById(Long id);

}