package com.desarrollo.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrollo.v1.model.Opcion;
import java.util.List;
import java.util.Optional;

@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Long> {
    List<Opcion> findByCategoria(String categoria);
    List<Opcion> findByDisponibleTrue();
    List<Opcion> findByCategoriaAndDisponibleTrue(String categoria, Boolean disponible);
    Optional<Opcion> findByNombre(String nombre);
    Optional<Opcion> findByNombreAndPrecio(String nombre, Double precio);
}
