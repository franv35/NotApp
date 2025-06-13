package com.imb2025.notapp.controller;


import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.entity.NoteTerminada;
import com.imb2025.notapp.entity.dto.RegisterRequest;
import com.imb2025.notapp.entity.dto.UpdateContentDTO;
import com.imb2025.notapp.entity.dto.UpdateNoteDTO;
import com.imb2025.notapp.entity.dto.UpdateTitleDTO;
import com.imb2025.notapp.service.INotesService;

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
    public ResponseEntity<String> updateTitle(@RequestBody UpdateTitleDTO dto){
        return ResponseEntity.ok(iNotesService.updateTitle(dto.getId(),dto.getTitle()));
    }
    @PutMapping("/editarcontenido")
    public ResponseEntity<String> updateContent(@RequestBody UpdateContentDTO dto){
        return ResponseEntity.ok(iNotesService.updateContent(dto.getId(),dto.getContent()));
    }

    @PutMapping("/editar")
    public ResponseEntity<String> updateNote (@RequestBody UpdateNoteDTO dto){
        return ResponseEntity.ok(iNotesService.updateNote(dto.getId(), dto.getTitle(), dto.getContent()));
    }
    @GetMapping("/obtenernotasterminadas")
    public ResponseEntity<List<NoteTerminada>> findAllTerminadas(){
        return ResponseEntity.ok(iNotesService.findAllTerminadas());
    }
    @PostMapping("/guardarnotaterminada/{id}")
    public ResponseEntity<String> notaTerminada(@PathVariable Long id){
        return ResponseEntity.ok(iNotesService.notaTerminada(id));
    }
}
