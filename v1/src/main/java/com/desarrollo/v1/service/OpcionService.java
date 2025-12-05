package com.desarrollo.v1.service;

import org.springframework.stereotype.Service;

import com.desarrollo.v1.repository.OpcionRepository;
import com.desarrollo.v1.model.Opcion;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class OpcionService {
    @Autowired
    OpcionRepository opcionRepository;

    public List<Opcion> obtenerTodasLasOpciones() {
        return opcionRepository.findAll();
    }

    public List<Opcion> obtenerOpcionesPorCategoria(String categoria) {
        return opcionRepository.findByCategoria(categoria);
    }

    public List<Opcion> obtenerOpcionesDisponibles() {
        return opcionRepository.findByDisponibleTrue();
    }

    public List<Opcion> obtenerOpcionesDisponiblesPorCategoria(String categoria) {
        return opcionRepository.findByCategoriaAndDisponibleTrue(categoria, true);
    }

    public Optional<Opcion> obtenerOpcionPorId(Long id) {
        return opcionRepository.findById(id);
    }

    public Opcion crearOpcion(String nombre, String descripcion, Double precio, String categoria, Boolean disponible) {
        Opcion opcion = new Opcion();
        opcion.setNombre(nombre);
        opcion.setDescripcion(descripcion);
        opcion.setPrecio(precio);
        opcion.setCategoria(categoria);
        opcion.setDisponible(disponible);
        opcion.setCreatedAt(LocalDateTime.now());
        opcion.setUpdatedAt(LocalDateTime.now());

        return opcionRepository.save(opcion);
    }

    public Opcion actualizarOpcion(Long id, String nombre, String descripcion, Double precio, String categoria, Boolean disponible) {
        Optional<Opcion> opcionOptional = opcionRepository.findById(id);
        if (opcionOptional.isPresent()) {
            Opcion opcion = opcionOptional.get();
            opcion.setNombre(nombre);
            opcion.setDescripcion(descripcion);
            opcion.setPrecio(precio);
            opcion.setCategoria(categoria);
            opcion.setDisponible(disponible);
            opcion.setUpdatedAt(LocalDateTime.now());
            return opcionRepository.save(opcion);
        }
        return null;
    }

    public boolean eliminarOpcion(Long id) {
        try {
            opcionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
