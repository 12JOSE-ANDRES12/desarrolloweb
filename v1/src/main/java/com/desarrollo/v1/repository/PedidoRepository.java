package com.desarrollo.v1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrollo.v1.model.Pedido;
import com.desarrollo.v1.model.usermodel;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario(usermodel usuario);
    List<Pedido> findByEstado(String estado);
    List<Pedido> findAllByOrderByCreatedAtDesc();
}
