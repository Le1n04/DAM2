package biblioteca.controladores;

import biblioteca.modelos.Libro;
import biblioteca.repositorios.LibroRepositorio;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroControlador {

    private final LibroRepositorio repositorio;

    public LibroControlador(LibroRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Libro> obtenerTodosLosLibros() {
        return repositorio.findAll();
    }

    @PostMapping
    public Libro agregarLibro(@RequestBody Libro libro) {
        return repositorio.save(libro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> modificarLibro(@PathVariable Long id, @RequestBody Libro detallesLibro) {
        return repositorio.findById(id).map(libro -> {
            libro.setTitulo(detallesLibro.getTitulo());
            libro.setAutor(detallesLibro.getAutor());
            libro.setPaginas(detallesLibro.getPaginas());
            libro.setGenero(detallesLibro.getGenero());
            libro.setDisponible(detallesLibro.isDisponible());
            repositorio.save(libro);
            return ResponseEntity.ok(libro);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscarLibrosPorTitulo(@RequestParam String titulo) {
        System.out.println("Buscando libros con título: " + titulo);
        List<Libro> librosEncontrados = repositorio.buscarPorTitulo(titulo);
        System.out.println("Total encontrados: " + librosEncontrados.size());
        return ResponseEntity.ok(librosEncontrados);
    }

    @GetMapping("/test")
    public ResponseEntity<List<Libro>> ejecutarPrueba() {
        return ResponseEntity.ok(repositorio.findAll());
    }
}
