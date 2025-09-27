package com.imb2025.notapp.controller;

import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.entity.NoteTerminada;
import com.imb2025.notapp.entity.dto.NoteResponseDTO;
import com.imb2025.notapp.entity.dto.RegisterRequest;
import com.imb2025.notapp.entity.dto.UpdateContentDTO;
import com.imb2025.notapp.entity.dto.UpdateNoteDTO;
import com.imb2025.notapp.entity.dto.UpdateTitleDTO;
import com.imb2025.notapp.service.INotesService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final INotesService iNotesService;

    public NoteController(INotesService iNotesService) {
        this.iNotesService = iNotesService;
    }

    // ✅ Devuelve todas las notas con etiquetas incluidas
    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> findAll() {
        List<Note> notes = iNotesService.findAll();
        List<NoteResponseDTO> dtos = notes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ✅ Devuelve una nota por ID con etiquetas incluidas
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> findById(@PathVariable Long id) {
        Note note = iNotesService.findById(id);
        return ResponseEntity.ok(mapToDTO(note));
    }

    @PostMapping("/crear")
    public ResponseEntity<String> createNote(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(iNotesService.createNote(request));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(iNotesService.deleteById(id));
    }

    @PutMapping("/editartitle")
    public ResponseEntity<String> updateTitle(@RequestBody UpdateTitleDTO dto) {
        return ResponseEntity.ok(iNotesService.updateTitle(dto.getId(), dto.getTitle()));
    }

    @PutMapping("/editarcontenido")
    public ResponseEntity<String> updateContent(@RequestBody UpdateContentDTO dto) {
        return ResponseEntity.ok(iNotesService.updateContent(dto.getId(), dto.getContent()));
    }

    @PutMapping("/editar")
    public ResponseEntity<String> updateNote(@RequestBody UpdateNoteDTO dto) {
        return ResponseEntity.ok(iNotesService.updateNote(dto.getId(), dto.getTitle(), dto.getContent()));
    }

    @GetMapping("/obtenernotasterminadas")
    public ResponseEntity<List<NoteTerminada>> findAllTerminadas() {
        return ResponseEntity.ok(iNotesService.findAllTerminadas());
    }

    @PostMapping("/guardarnotaterminada/{id}")
    public ResponseEntity<String> notaTerminada(@PathVariable Long id) {
        return ResponseEntity.ok(iNotesService.notaTerminada(id));
    }

    @DeleteMapping("/borrarterminada/{id}")
    public ResponseEntity<String> deleteTerminada(@PathVariable Long id) {
        return ResponseEntity.ok(iNotesService.deleteTerminadaById(id));
    }

    // ✅ Agrega una etiqueta a una nota y devuelve la nota actualizada con etiquetas
    @PutMapping("/agregaretiqueta/{id}")
    public ResponseEntity<NoteResponseDTO> agregarEtiqueta(@PathVariable Long id, @RequestBody String nombreEtiqueta) {
        Note updatedNote = iNotesService.agregarEtiquetaANota(id, nombreEtiqueta);
        return ResponseEntity.ok(mapToDTO(updatedNote));
    }

    // ✅ Mapper interno para convertir Note → NoteResponseDTO
    private NoteResponseDTO mapToDTO(Note note) {
        NoteResponseDTO dto = new NoteResponseDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContenido(note.getContenido());
        dto.setEtiquetas(note.getEtiquetas());
        return dto;
    }
}
