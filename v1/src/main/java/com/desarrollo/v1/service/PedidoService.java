package com.desarrollo.v1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desarrollo.v1.model.Pedido;
import com.desarrollo.v1.model.usermodel;
import com.desarrollo.v1.repository.PedidoRepository;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    public Pedido crearPedido(usermodel usuario, Double subtotal, Double impuestos, Double envio, String descripcion) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setSubtotal(subtotal);
        pedido.setImpuestos(impuestos);
        pedido.setEnvio(envio);
        pedido.setTotal(subtotal + impuestos + envio);
        pedido.setEstado("PENDIENTE");
        pedido.setDescripcion(descripcion);
        pedido.setCreatedAt(LocalDateTime.now());
        pedido.setUpdatedAt(LocalDateTime.now());
        
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodosPedidos() {
        return pedidoRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Pedido> obtenerPedidosPorUsuario(usermodel usuario) {
        return pedidoRepository.findByUsuario(usuario);
    }

    public List<Pedido> obtenerPedidosPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public Optional<Pedido> obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public void actualizarEstadoPedido(Long id, String estado) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            pedido.get().setEstado(estado);
            pedido.get().setUpdatedAt(LocalDateTime.now());
            pedidoRepository.save(pedido.get());
        }
    }

    public boolean eliminarPedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
