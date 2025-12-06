package com.desarrollo.v1.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Entity
@Table(name = "tbl_pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Order", description = "Representa un pedido realizado por un usuario")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del pedido", example = "1")
    Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedidos_usuario"))
    @Schema(description = "Usuario que realizó el pedido")
    usermodel usuario;

    @Column(name = "subtotal", nullable = false)
    @Schema(description = "Subtotal del pedido", example = "50.0")
    Double subtotal;

    @Column(name = "impuestos", nullable = false)
    @Schema(description = "Impuestos del pedido", example = "5.0")
    Double impuestos;

    @Column(name = "envio", nullable = false)
    @Schema(description = "Costo de envío", example = "5.0")
    Double envio;

    @Column(name = "total", nullable = false)
    @Schema(description = "Total del pedido", example = "60.0")
    Double total;

    @Column(name = "estado", nullable = false)
    @Schema(description = "Estado del pedido (PENDIENTE, PROCESANDO, COMPLETADO, CANCELADO)", example = "PENDIENTE")
    String estado;

    @Column(name = "descripcion", length = 500)
    @Schema(description = "Descripción del pedido", example = "Pedido de 2 tacos al pastor")
    String descripcion;

    @Column(name = "created_at", nullable = false)
    @Schema(description = "Fecha de creación del pedido")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Fecha de última actualización")
    LocalDateTime updatedAt;
}
