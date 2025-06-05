package com.imb2025.notapp.entity.dto;

import com.imb2025.notapp.entity.Etiqueta;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class RegisterRequest {
    private String title;
    private String contenido;
    private List<Etiqueta> etiquetas;
}
