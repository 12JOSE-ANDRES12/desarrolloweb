package com.desarrollo.v1.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User", description = "Representa un usuario del sistema")
public class usermodel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Schema(description = "Identificador único del usuario", example = "1")
    Long id;

   @Column(name ="nombre",length= 50, nullable = false)
   @Schema(description = "Nombre completo del usuario", example = "Juan Perez")
   String name;
   
   @Column(name ="email", length = 100, nullable = false, unique = true)
   @Schema(description = "Correo electrónico", example = "juan@ejemplo.com")
   String email;
   
   @Column(name ="password", nullable = false)
   @Schema(description = "Contraseña (hashed) del usuario", example = "********")
   String password;
   
   @Column(name = "role", nullable = false)
   @Enumerated(EnumType.STRING)
   @Schema(description = "Rol del usuario (ADMIN o USER)", example = "USER")
   UserRole role;
   
   @Column(name ="created_at")
   @Schema(description = "Fecha de creación del usuario")
   LocalDateTime createdAt;
}
