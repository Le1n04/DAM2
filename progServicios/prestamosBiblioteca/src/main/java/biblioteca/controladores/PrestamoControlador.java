package biblioteca.controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import biblioteca.service.*;
import biblioteca.entity.*;

import biblioteca.modelos.Prestamo;
import biblioteca.repositorios.LectorRepositorio;
import biblioteca.repositorios.LibroRepositorio;
import biblioteca.repositorios.PrestamoRepositorio;

@RestController
@RequestMapping("/prestamos")
@RestController
@RequestMapping("/api")
public class PrestamoControlador {

    @Autowired
private PrestamoRepositorio repositorio;
    @Autowired
private LibroRepositorio libroRepo;
    @Autowired
private LectorRepositorio lectorRepo;

    public PrestamoControlador(PrestamoRepositorio repositorio, LibroRepositorio libroRepo, LectorRepositorio lectorRepo) {
        this.repositorio = repositorio;
        this.libroRepo = libroRepo;
        this.lectorRepo = lectorRepo;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> crearPrestamo(@RequestBody Map<String, Long> datos) {
        Long libroId = datos.get("libroId");
        Long lectorId = datos.get("lectorId");

        if (libroId == null || lectorId == null) {
            return ResponseEntity.badRequest().body("Faltan datos: libroId o lectorId");
        }

        if (!libroRepo.existsById(libroId)) {
            return ResponseEntity.badRequest().body("El libro con ID " + libroId + " no existe.");
        }

        if (!lectorRepo.existsById(lectorId)) {
            return ResponseEntity.badRequest().body("El lector con ID " + lectorId + " no existe.");
        }

        Prestamo nuevoPrestamo = new Prestamo(libroId, lectorId, LocalDate.now().toString(), null);
        repositorio.save(nuevoPrestamo);
        return ResponseEntity.ok("Préstamo registrado con éxito.");
    }

    @PostMapping("/devolver/{id}")
    public ResponseEntity<String> devolverLibro(@PathVariable Long id) {
        return repositorio.findById(id).map(prestamo -> {
            if (prestamo.getFechabaja() != null) {
                return ResponseEntity.badRequest().body("Este préstamo ya fue devuelto.");
            }
            prestamo.setFechabaja(LocalDate.now().toString());
            repositorio.save(prestamo);
            return ResponseEntity.ok("Libro devuelto correctamente.");
        }).orElseGet(() -> ResponseEntity.badRequest().body("Préstamo no encontrado."));
    }

    @GetMapping("/lector/{idLector}")
    public ResponseEntity<List<Prestamo>> listarPrestamosPorLector(@PathVariable Long idLector) {
        List<Prestamo> prestamos = repositorio.findByIdLector(idLector);
        return prestamos.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.ok(prestamos);
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<String> cerrarPrestamo(@PathVariable Long id) {
        return repositorio.findById(id).map(prestamo -> {
            if (prestamo.getFechabaja() == null) {
                prestamo.setFechabaja(LocalDate.now().toString());
                repositorio.save(prestamo);
                return ResponseEntity.ok("Préstamo finalizado correctamente.");
            }
            return ResponseEntity.badRequest().body("Este préstamo ya ha sido finalizado.");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
