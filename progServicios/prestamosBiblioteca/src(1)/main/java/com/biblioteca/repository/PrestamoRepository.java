package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    boolean existsByLectorIdAndFechaBajaIsNull(Long lectorId);
}
