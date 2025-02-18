package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Lector;
import repository.LectorRepository;

@RestController
@RequestMapping("/lectores") // 👈 Asegurar que la ruta es correcta
@CrossOrigin(origins = "*") // Habilita CORS para pruebas en Postman
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
