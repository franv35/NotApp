package com.imb2025.notapp.service;

import com.imb2025.notapp.entity.Etiqueta;
import com.imb2025.notapp.entity.Note;
import com.imb2025.notapp.entity.Usuario;
import com.imb2025.notapp.enums.EstadoNota;
import com.imb2025.notapp.entity.dto.RegisterRequest;
import com.imb2025.notapp.repository.NotesRepository;
import com.imb2025.notapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesServiceImp implements INotesService {

    @Autowired
    private NotesRepository noteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EtiquetaService etiquetaService;

    @Override
    public List<Note> findAllByUsuario(String username) {
        return noteRepository.findByUsuarioUsername(username);
    }

    @Override
    public Note findByIdAndUsuario(Long id, String username) {
        return noteRepository.findByIdAndUsuarioUsername(id, username)
                .orElseThrow(() -> new RuntimeException("La nota no existe o no pertenece al usuario"));
    }

    @Override
    public Note createNote(RegisterRequest request, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Note note = new Note();
        note.updateTitle(request.getTitle());
        note.updateContenido(request.getContenido());
        note.setEstado(EstadoNota.CREADA);
        note.setUsuario(usuario);

        return noteRepository.save(note);
    }

    @Override
    public void deleteByIdAndUsuario(Long id, String username) {
        Note note = findByIdAndUsuario(id, username);
        noteRepository.delete(note);
    }

    @Override
    public Note updateTitle(Long id, String title, String username) {
        Note note = findByIdAndUsuario(id, username);
        note.updateTitle(title);
        return noteRepository.save(note);
    }

    @Override
    public Note updateContent(Long id, String content, String username) {
        Note note = findByIdAndUsuario(id, username);
        note.updateContenido(content);
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Long id, String title, String content, String username) {
        Note note = findByIdAndUsuario(id, username);
        note.updateTitle(title);
        note.updateContenido(content);
        return noteRepository.save(note);
    }

    @Override
    public List<Note> findByEstadoAndUsuario(EstadoNota estado, String username) {
        return noteRepository.findByEstadoAndUsuarioUsername(estado, username);
    }

    @Override
    public Note agregarEtiquetaANota(Long noteId, String nombreEtiqueta, String username) {
        Note note = findByIdAndUsuario(noteId, username);

        Etiqueta etiqueta = etiquetaService.findByNombre(nombreEtiqueta)
                .orElseGet(() -> {
                    Etiqueta nueva = new Etiqueta();
                    nueva.setNombre(nombreEtiqueta);
                    return etiquetaService.save(nueva);
                });

        if (!note.getEtiquetas().contains(etiqueta)) {
            note.getEtiquetas().add(etiqueta);
            noteRepository.save(note);
        }

        return note;
    }

    @Override
    public Note agregarColaboradorANota(Long noteId, Long colaboradorId, String username) {
        Note note = findByIdAndUsuario(noteId, username);
        // lógica para buscar colaborador y agregarlo
        // note.getColaboradores().add(colaborador);
        return noteRepository.save(note);
    }

    @Override
    public Note agregarRecursoANota(Long noteId, Long recursoId, String username) {
        Note note = findByIdAndUsuario(noteId, username);
        // lógica para buscar recurso y agregarlo
        // note.getRecursos().add(recurso);
        return noteRepository.save(note);
    }
}
