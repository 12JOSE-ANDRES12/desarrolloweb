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
@Table(name = "tbl_users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class usermodel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

   @Column(name ="nombre",length= 50, nullable = false)
   String name;
   
   @Column(name ="email", length = 100, nullable = false, unique = true)
   String email;
   
   @Column(name ="password", nullable = false)
   String password;
   
   @Column(name ="created_at")
   LocalDateTime createdAt;
}
