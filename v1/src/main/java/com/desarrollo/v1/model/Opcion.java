package com.desarrollo.v1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_opciones")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Opcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    String nombre;

    @Column(name = "descripcion", length = 255)
    String descripcion;

    @Column(name = "precio", nullable = false)
    Double precio;

    @Column(name = "categoria", length = 50, nullable = false)
    String categoria;

    @Column(name = "disponible", nullable = false)
    Boolean disponible;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
