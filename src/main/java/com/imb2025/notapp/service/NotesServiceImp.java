package com.imb2025.notapp.service;

import com.imb2025.notapp.entity.Note;

import java.util.List;


import com.imb2025.notapp.service.jpa.NoteService.INoteServiceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesServiceImp implements INotesService {

    @Autowired
    private INoteServiceProcessor inoteService;

    @Override
    public List<Note> findAll() {
    return inoteService.findAll();
    }
    
    @Override
    public Note findById(Long id) {
        return inoteService.findById(id);
    }
    @Override
    public String deleteById(Long id){
        return inoteService.deleteById(id);
    }

    @Override
    public String updateTitle(String title){
        return inoteService.updateTitle(title);
    }

    @Override
    public String updateContent(String content) {
        return inoteService.updateContent(title);
    }


}