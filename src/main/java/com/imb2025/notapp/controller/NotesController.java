package com.imb2025.notapp.controller;

import com.imb2025.notapp.service.NotesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notes")
public class NotesController {

    @Autowired
    private NotesService iservice

    @GetMapping
    public List<Note> getAll() {

        return iservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notes> getById(@PathVariable Long id) {
    	Notes notes = service.findById(id);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Notes> create(@RequestBody Notes notes) {
    	Notes createdNote = service.save(notes);
        return ResponseEntity.ok(createdNote);
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}