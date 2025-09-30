package com.imb2025.notapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notesterminadas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteTerminada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contenido;

    @ManyToMany
    @JoinTable(
            name = "nota_terminada_etiqueta",
            joinColumns = @JoinColumn(name = "nota_terminada_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private List<Etiqueta> etiquetas = new ArrayList<>();
}
