package com.imb2025.notapp.entity.dto;

import com.imb2025.notapp.entity.Etiqueta;
import lombok.Data;

import java.util.List;

@Data
public class NoteResponseDTO {
    private Long id;
    private String title;
    private String contenido;
    private List<Etiqueta> etiquetas;
}
