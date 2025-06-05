package com.imb2025.notapp.controller;


import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.entity.dto.RegisterRequest;
import com.imb2025.notapp.service.INotesService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final INotesService iNotesService;

    public NoteController(INotesService iNotesService) {
        this.iNotesService = iNotesService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> findAll(){
        return  ResponseEntity.ok(iNotesService.findAll());
    }

    @PostMapping("/crear")
    public ResponseEntity<String> createNote(@RequestBody RegisterRequest request){
    return ResponseEntity.ok(iNotesService.createNote(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id){
        return ResponseEntity.ok(iNotesService.findById(id));
    }
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(iNotesService.deleteById(id));
    }
    @PutMapping("/editartitle")
    public ResponseEntity<String> updateTitle(@RequestBody Long id,String title){
        return ResponseEntity.ok(iNotesService.updateTitle(id,title));
    }
    @PutMapping("/editarcontenido")
    public ResponseEntity<String> updateContent(@RequestBody Long id,String content){
        return ResponseEntity.ok(iNotesService.updateContent(id,content));
    }
}
