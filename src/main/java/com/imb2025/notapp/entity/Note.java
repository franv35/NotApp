package com.imb2025.notapp.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contenido;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "note_etiquetas",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private List<Etiqueta> etiquetas = new ArrayList<>();


    public void updateTitle(String title){
        if (title==null || title.isBlank())throw new RuntimeException("El titulo no puede estar en blanco");
        this.title=title;
    }
    public void updateContenido(String contenido){
        if (contenido==null || contenido.isBlank())throw new RuntimeException("El contenido no puede estar en blanco");
        this.contenido=contenido;
    }

    }










