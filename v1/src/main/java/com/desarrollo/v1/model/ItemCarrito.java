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
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tbl_items_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CartItem", description = "Elemento del carrito de compras")
public class ItemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del item", example = "10")
    Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_items_carrito_usuario"))
    @Schema(description = "Usuario propietario del item")
    usermodel usuario;

    @ManyToOne
    @JoinColumn(name = "opcion_id", nullable = false, foreignKey = @ForeignKey(name = "fk_items_carrito_opcion"))
    @Schema(description = "Opción asociada al item")
    Opcion opcion;

    @Column(name = "cantidad", nullable = false)
    @Schema(description = "Cantidad del producto", example = "2")
    Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    @Schema(description = "Precio unitario en moneda local", example = "12.5")
    Double precioUnitario;

    @Column(name = "subtotal", nullable = false)
    @Schema(description = "Subtotal (cantidad * precio_unitario)", example = "25.0")
    Double subtotal;

    @Column(name = "notas", length = 500)
    @Schema(description = "Notas del item", example = "Sin cebolla")
    String notas;

    @Column(name = "created_at")
    @Schema(description = "Fecha de creación del item")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Fecha de última actualización")
    LocalDateTime updatedAt;
}
