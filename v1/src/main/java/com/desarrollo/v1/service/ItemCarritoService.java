package com.desarrollo.v1.service;

import org.springframework.stereotype.Service;

import com.desarrollo.v1.repository.ItemCarritoRepository;
import com.desarrollo.v1.repository.OpcionRepository;
import com.desarrollo.v1.model.ItemCarrito;
import com.desarrollo.v1.model.Opcion;
import com.desarrollo.v1.model.usermodel;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class ItemCarritoService {
    @Autowired
    ItemCarritoRepository itemCarritoRepository;

    @Autowired
    OpcionRepository opcionRepository;

    public List<ItemCarrito> obtenerCarritoDelUsuario(usermodel usuario) {
        return itemCarritoRepository.findByUsuario(usuario);
    }

    public List<ItemCarrito> obtenerCarritoDelUsuarioPorId(Long usuarioId) {
        return itemCarritoRepository.findByUsuarioId(usuarioId);
    }

    public ItemCarrito agregarAlCarrito(usermodel usuario, Long opcionId, Integer cantidad, String notas) {
        Optional<Opcion> opcionOptional = opcionRepository.findById(opcionId);
        if (!opcionOptional.isPresent()) {
            return null;
        }

        Opcion opcion = opcionOptional.get();
        ItemCarrito item = new ItemCarrito();
        item.setUsuario(usuario);
        item.setOpcion(opcion);
        item.setCantidad(cantidad);
        item.setPrecioUnitario(opcion.getPrecio());
        item.setSubtotal(opcion.getPrecio() * cantidad);
        item.setNotas(notas);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        return itemCarritoRepository.save(item);
    }

    public ItemCarrito actualizarCantidad(Long itemId, Integer nuevaCantidad) {
        Optional<ItemCarrito> itemOptional = itemCarritoRepository.findById(itemId);
        if (itemOptional.isPresent()) {
            ItemCarrito item = itemOptional.get();
            item.setCantidad(nuevaCantidad);
            item.setSubtotal(item.getPrecioUnitario() * nuevaCantidad);
            item.setUpdatedAt(LocalDateTime.now());
            return itemCarritoRepository.save(item);
        }
        return null;
    }

    public ItemCarrito obtenerItemCarrito(Long id) {
        return itemCarritoRepository.findById(id).orElse(null);
    }

    public boolean eliminarDelCarrito(Long itemId) {
        try {
            itemCarritoRepository.deleteById(itemId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void vaciarCarrito(usermodel usuario) {
        itemCarritoRepository.deleteByUsuario(usuario);
    }

    public void vaciarCarritoPorUsuarioId(Long usuarioId) {
        itemCarritoRepository.deleteByUsuarioId(usuarioId);
    }

    public Double calcularSubtotalCarrito(usermodel usuario) {
        List<ItemCarrito> items = itemCarritoRepository.findByUsuario(usuario);
        return items.stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
    }

    public Double calcularTotalConImpuestos(usermodel usuario) {
        Double subtotal = calcularSubtotalCarrito(usuario);
        Double impuestos = subtotal * 0.10; // 10% de impuesto
        return subtotal + impuestos;
    }
}
