package com.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.Lector;
import com.biblioteca.repository.LectorRepository;

@RestController
@RequestMapping("/api/lectores")
@CrossOrigin(origins = "*")
public class LectorController {
    private final LectorRepository lectorRepository;

    public LectorController(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    // Obtener todos los lectores
    @GetMapping
    public List<Lector> listarLectores() {
        return lectorRepository.findAll();
    }

    // Obtener un lector por ID
    @GetMapping("/{id}")
    public ResponseEntity<Lector> obtenerLector(@PathVariable Long id) {
        Optional<Lector> lector = lectorRepository.findById(id);
        return lector.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
