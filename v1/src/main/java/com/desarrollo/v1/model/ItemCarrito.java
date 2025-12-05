package com.desarrollo.v1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_items_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_items_carrito_usuario"))
    usermodel usuario;

    @ManyToOne
    @JoinColumn(name = "opcion_id", nullable = false, foreignKey = @ForeignKey(name = "fk_items_carrito_opcion"))
    Opcion opcion;

    @Column(name = "cantidad", nullable = false)
    Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    Double precioUnitario;

    @Column(name = "subtotal", nullable = false)
    Double subtotal;

    @Column(name = "notas", length = 500)
    String notas;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
