package com.imb2025.notapp.entity.dto;

import com.imb2025.notapp.entity.Etiqueta;
import com.imb2025.notapp.entity.Usuario;
import com.imb2025.notapp.entity.Recurso;
import com.imb2025.notapp.enums.EstadoNota;
import lombok.Data;

import java.util.List;

@Data
public class NoteResponseDTO {
    private Long id;
    private String title;
    private String contenido;
    private EstadoNota estado;
    private Usuario usuario;
    private List<Etiqueta> etiquetas;
    private List<Usuario> colaboradores;
    private List<Recurso> recursos;
}
