package biblioteca.controladores;

import biblioteca.modelos.Lector;
import biblioteca.repositorios.LectorRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectores")
@RestController
@RequestMapping("/api")
public class LectorControlador {

    @Autowired
private LectorRepositorio repositorio;

    public LectorControlador(LectorRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @PostMapping
    public ResponseEntity<Lector> crearLector(@RequestBody Lector lector) {
        if (esLectorInvalido(lector)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(repositorio.save(lector));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Lector>> obtenerLectoresPorNombre(@PathVariable String nombre) {
        List<Lector> listaLectores = repositorio.findByNombreContainingIgnoreCase(nombre);
        return listaLectores.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(listaLectores);
    }

    private boolean esLectorInvalido(Lector lector) {
        return lector.getNombre() == null || lector.getNombre().trim().isEmpty() ||
               lector.getNombrelogin() == null || lector.getNombrelogin().trim().isEmpty() ||
               lector.getContraseña() == null || lector.getContraseña().trim().isEmpty();
    }
}
