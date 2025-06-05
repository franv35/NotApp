package com.imb2025.notapp.service;

import com.imb2025.notapp.entity.Note;

import java.util.List;
import java.util.Locale;
import java.util.Optional;


import com.imb2025.notapp.entity.dto.RegisterRequest;
import com.imb2025.notapp.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesServiceImp implements INotesService {

    @Autowired
    private NotesRepository noteRepository;

    @Override
    public List<Note> findAll() {
        List<Note> notesOpt= noteRepository.findAll();
        if (notesOpt.isEmpty())throw new RuntimeException("lista vacia");
        return notesOpt;
    }
    
    @Override
    public Note findById(Long id) {
    Note note=findByIdRepository(id);

    return note;
    }

    @Override
    public String createNote(RegisterRequest request) {
       Optional<Note> noteOpt=findByTitleRepository(request.getTitle());
       if (noteOpt.isPresent())throw new RuntimeException("La nota ya existe");
       Note note=new Note();
        note.updateTitle(request.getTitle());
        note.updateContenido(request.getContenido());
       note.setEtiquetas(request.getEtiquetas());
       noteRepository.save(note);
       return "Nota creado exitosamente";
    }
    private Optional<Note> findByTitleRepository(String title){
        return noteRepository.findByTitle(title);
    }

    @Override
    public String deleteById(Long id){
        Note note=findByIdRepository(id);
        noteRepository.deleteById(note.getId());
        return "Borrado exitosamente";

    }

    @Override
    public String updateTitle(Long id, String title){
       Note note= findByIdRepository(id);
        note.updateTitle(title);
        noteRepository.save(note);
        return "Se ha editado correctamente el titulo";
    }



    @Override
    public String updateContent(Long id, String content) {
        Note note= findByIdRepository(id);
        note.updateContenido(content);
        noteRepository.save(note);
        return "Se ha editado correctamente el contenido";
    }

    private Note findByIdRepository(Long id){
        return noteRepository.findById(id).orElseThrow(()->new RuntimeException("La nota no existe"));
    }




}