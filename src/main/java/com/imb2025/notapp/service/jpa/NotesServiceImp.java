package com.imb2025.notapp.service.jpa;

import com.imb2025.notapp.entity.Notes;
import com.imb2025.notapp.repository.NotesRepository;
import com.imb2025.notapp.service.NotesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesServiceImp implements NotesService {

    @Autowired
    private NotesRepository repository;

    @Override
    public List<Notes> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Notes findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Notes save(Notes estadoCursada) {
        return repository.save(estadoCursada);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Notes update(Long id, Notes estadoCursada) {
        Notes existingNote = repository.findById(id).orElse(null);
        if (existingNote == null) {
            return null;
        }
        existingNote.setTitulo(estadoCursada.getTitulo());
        existingNote.setContenido(estadoCursada.getContenido());
        return repository.save(existingNote);
    }

}