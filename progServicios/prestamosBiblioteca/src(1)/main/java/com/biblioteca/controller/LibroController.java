package com.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {
    private final LibroRepository libroRepository;

    public LibroController(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @GetMapping
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibro(@PathVariable Long id) {
        Optional<Libro> libro = libroRepository.findById(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        return libroRepository.findById(id).map(libro -> {
            libro.setTitulo(libroDetalles.getTitulo());
            libro.setAutor(libroDetalles.getAutor());
            libro.setPaginas(libroDetalles.getPaginas());
            libro.setGenero(libroDetalles.getGenero());
            libro.setDisponible(libroDetalles.getDisponible());
            return ResponseEntity.ok(libroRepository.save(libro));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        if (!libroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        libroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
