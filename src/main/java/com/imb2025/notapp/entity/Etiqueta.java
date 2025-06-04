package com.imb2025.notapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etiqueta {
    private String nombreEti;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEti;
}
