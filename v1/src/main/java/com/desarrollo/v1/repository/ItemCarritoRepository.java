package com.desarrollo.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrollo.v1.model.ItemCarrito;
import com.desarrollo.v1.model.usermodel;
import java.util.List;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    List<ItemCarrito> findByUsuario(usermodel usuario);
    List<ItemCarrito> findByUsuarioId(Long usuarioId);
    void deleteByUsuario(usermodel usuario);
    void deleteByUsuarioId(Long usuarioId);
}
