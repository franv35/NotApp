package com.imb2025.notapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String titulo;
    private String contenido;
    private boolean estado = false; // predeterminada como NUEVA

    @OneToMany
    private List<Etiqueta> etiquetas;






	
}
