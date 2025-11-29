package com.imb2025.notapp.controller;

import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.entity.dto.NoteResponseDTO;
import com.imb2025.notapp.entity.dto.RegisterRequest;
import com.imb2025.notapp.entity.dto.UpdateContentDTO;
import com.imb2025.notapp.entity.dto.UpdateNoteDTO;
import com.imb2025.notapp.entity.dto.UpdateTitleDTO;
import com.imb2025.notapp.enums.EstadoNota;
import com.imb2025.notapp.service.INotesService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final INotesService iNotesService;

    public NoteController(INotesService iNotesService) {
        this.iNotesService = iNotesService;
    }

    // ✅ Devuelve todas las notas del usuario loggeado
    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> findAll(@AuthenticationPrincipal Principal principal) {
        List<Note> notes = iNotesService.findAllByUsuario(principal.getName());
        List<NoteResponseDTO> dtos = notes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ✅ Devuelve una nota por ID (solo si pertenece al usuario)
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> findById(@PathVariable Long id, @AuthenticationPrincipal Principal principal) {
        Note note = iNotesService.findByIdAndUsuario(id, principal.getName());
        return ResponseEntity.ok(mapToDTO(note));
    }

    // ✅ Crear nueva nota asociada al usuario loggeado
    @PostMapping("/crear")
    public ResponseEntity<NoteResponseDTO> createNote(@RequestBody RegisterRequest request,
                                                      @AuthenticationPrincipal Principal principal) {
        Note note = iNotesService.createNote(request, principal.getName());
        return ResponseEntity.ok(mapToDTO(note));
    }

    // ✅ Eliminar nota (solo si pertenece al usuario)
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @AuthenticationPrincipal Principal principal) {
        iNotesService.deleteByIdAndUsuario(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    // ✅ Editar título
    @PutMapping("/editartitle")
    public ResponseEntity<NoteResponseDTO> updateTitle(@RequestBody UpdateTitleDTO dto,
                                                       @AuthenticationPrincipal Principal principal) {
        Note updated = iNotesService.updateTitle(dto.getId(), dto.getTitle(), principal.getName());
        return ResponseEntity.ok(mapToDTO(updated));
    }

    // ✅ Editar contenido
    @PutMapping("/editarcontenido")
    public ResponseEntity<NoteResponseDTO> updateContent(@RequestBody UpdateContentDTO dto,
                                                         @AuthenticationPrincipal Principal principal) {
        Note updated = iNotesService.updateContent(dto.getId(), dto.getContent(), principal.getName());
        return ResponseEntity.ok(mapToDTO(updated));
    }

    // ✅ Editar título y contenido
    @PutMapping("/editar")
    public ResponseEntity<NoteResponseDTO> updateNote(@RequestBody UpdateNoteDTO dto,
                                                      @AuthenticationPrincipal Principal principal) {
        Note updated = iNotesService.updateNote(dto.getId(), dto.getTitle(), dto.getContent(), principal.getName());
        return ResponseEntity.ok(mapToDTO(updated));
    }

    // ✅ Filtrar notas por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<NoteResponseDTO>> findByEstado(@PathVariable EstadoNota estado,
                                                              @AuthenticationPrincipal Principal principal) {
        List<Note> notes = iNotesService.findByEstadoAndUsuario(estado, principal.getName());
        List<NoteResponseDTO> dtos = notes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ✅ Agregar etiqueta
    @PutMapping("/{id}/agregaretiqueta")
    public ResponseEntity<NoteResponseDTO> agregarEtiqueta(@PathVariable Long id,
                                                           @RequestBody String nombreEtiqueta,
                                                           @AuthenticationPrincipal Principal principal) {
        Note updatedNote = iNotesService.agregarEtiquetaANota(id, nombreEtiqueta, principal.getName());
        return ResponseEntity.ok(mapToDTO(updatedNote));
    }

    // ✅ Agregar colaborador
    @PutMapping("/{id}/agregarcolaborador")
    public ResponseEntity<NoteResponseDTO> agregarColaborador(@PathVariable Long id,
                                                              @RequestBody Long colaboradorId,
                                                              @AuthenticationPrincipal Principal principal) {
        Note updatedNote = iNotesService.agregarColaboradorANota(id, colaboradorId, principal.getName());
        return ResponseEntity.ok(mapToDTO(updatedNote));
    }

    // ✅ Agregar recurso
    @PutMapping("/{id}/agregarrecurso")
    public ResponseEntity<NoteResponseDTO> agregarRecurso(@PathVariable Long id,
                                                          @RequestBody Long recursoId,
                                                          @AuthenticationPrincipal Principal principal) {
        Note updatedNote = iNotesService.agregarRecursoANota(id, recursoId, principal.getName());
        return ResponseEntity.ok(mapToDTO(updatedNote));
    }

    // ✅ Mapper interno para convertir Note → NoteResponseDTO
    private NoteResponseDTO mapToDTO(Note note) {
        NoteResponseDTO dto = new NoteResponseDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContenido(note.getContenido());
        dto.setEtiquetas(note.getEtiquetas());
        dto.setColaboradores(note.getColaboradores());
        dto.setRecursos(note.getRecursos());
        dto.setEstado(note.getEstado());
        return dto;
    }
}
