package com.imb2025.notapp.service.jpa.NoteService.imp;

import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.repository.NotesRepository;

import com.imb2025.notapp.service.jpa.NoteService.INoteServiceProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NotesServiceProcessorImp implements INoteServiceProcessor {
    @Autowired
    private NotesRepository noteRepository;


    @Override
    public List<Note> findAll() {
        List<Note> notes= noteRepository.findAll();
        if (notes.isEmpty()){
            throw Exception;
        }
        return notes;
    }


    @Override
    public Note findById(Long id) {
        Optional<Note> noteOptional=noteRepository.findById(id).orElseThrow(()->Exception());
        Note note=noteOptional.get();
        return note;

    }

    @Override
    public String deleteById(Long id) {
        noteRepository.findById(id).orElseThrow(()->Exception());
        noteRepository.deleteById(id);
        return "Eliminado exitosamente";
    }

    @Override
    public String updateTitle(String title){
        Optional<Note> noteOpt=noteRepository.findByTitle(title).orElseThrow(()->Exception);
        Note note=noteOpt.get();
        note.setTitulo(title);
        noteRepository.save(note);
    }


}
