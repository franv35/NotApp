package com.imb2025.notapp.service;

import com.imb2025.notapp.entity.Etiqueta;
import com.imb2025.notapp.entity.Note;

import java.util.List;
import java.util.Optional;


import com.imb2025.notapp.entity.NoteTerminada;
import com.imb2025.notapp.entity.dto.RegisterRequest;
import com.imb2025.notapp.repository.NoteTerminadaRepository;
import com.imb2025.notapp.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesServiceImp implements INotesService {

    @Autowired
    private NotesRepository noteRepository;
    @Autowired
    private NoteTerminadaRepository noteTerminadaRepository;

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll(); // sin lanzar excepción
    }


    @Override
    public Note findById(Long id) {
    return findByIdRepository(id);
    }

    @Override
    public String createNote(RegisterRequest request) {
       Optional<Note> noteOpt=findByTitleRepository(request.getTitle());
       if (noteOpt.isPresent())throw new RuntimeException("La nota ya existe");
       Note note=new Note();
        note.updateTitle(request.getTitle());

        note.updateContenido(request.getContenido());

       noteRepository.save(note);
       return "Nota creada exitosamente";
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
    public String updateNote(Long id, String title, String content) {
        Note note = findByIdRepository(id);
        note.updateContenido(content);
        note.updateTitle(title);
        noteRepository.save(note);
        return "Se ha editado correctamente la nota";

    }




    @Override
    public String updateContent(Long id, String content) {
        Note note= findByIdRepository(id);
        note.updateContenido(content);
        noteRepository.save(note);
        return "Se ha editado correctamente el contenido";
    }

    @Override
    public List<NoteTerminada> findAllTerminadas() {
        return noteTerminadaRepository.findAll(); // sin lanzar excepción
    }


    @Override
    public String notaTerminada(Long id) {
        Note note=findByIdRepository(id);
        NoteTerminada noteTerminada=new NoteTerminada();
        noteTerminada.setTitle(note.getTitle());
        noteTerminada.setContenido(note.getContenido());
        noteTerminadaRepository.save(noteTerminada);
        noteRepository.delete(note);

        return"Borrada con exito";
    }

    private Note findByIdRepository(Long id){
        return noteRepository.findById(id).orElseThrow(()->new RuntimeException("La nota no existe"));
    }

    @Override
    public String deleteTerminadaById(Long id) {
        NoteTerminada nota = noteTerminadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La nota terminada no existe"));
        noteTerminadaRepository.delete(nota);
        return "Nota terminada eliminada";
    }



}