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
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tbl_opciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Option", description = "Opción de producto en el menú")
public class Opcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador de la opción", example = "5")
    Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    @Schema(description = "Nombre del producto", example = "Taco al Pastor")
    String nombre;

    @Column(name = "descripcion", length = 255)
    @Schema(description = "Descripción breve", example = "Taco con piña y salsa")
    String descripcion;

    @Column(name = "precio", nullable = false)
    @Schema(description = "Precio en moneda local", example = "3.5")
    Double precio;

    @Column(name = "categoria", length = 50, nullable = false)
    @Schema(description = "Categoría del producto", example = "Tacos")
    String categoria;

    @Column(name = "disponible", nullable = false)
    @Schema(description = "Disponibilidad del producto", example = "true")
    Boolean disponible;

    @Column(name = "created_at")
    @Schema(description = "Fecha de creación")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Fecha de actualización")
    LocalDateTime updatedAt;
}
