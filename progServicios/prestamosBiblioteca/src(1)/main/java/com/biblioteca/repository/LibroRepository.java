package com.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContaining(String titulo);
}
