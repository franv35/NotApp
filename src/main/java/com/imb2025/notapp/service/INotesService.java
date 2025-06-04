package com.imb2025.notapp.service;

import java.util.List;

import com.imb2025.notapp.entity.Note;

public interface INotesService {

    Note findById(Long id);

    List<Note> findAll();

    String deleteById(Long id);
    String updateTitle(String title);
    String updateContent(String content);
}