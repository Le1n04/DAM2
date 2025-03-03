package biblioteca.controladores;

import biblioteca.modelos.Libro;
import biblioteca.repositorios.LibroRepositorio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroControlador.class)
@RestController
@RequestMapping("/api")
public class LibroControladorTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LibroRepositorio libroRepositorio;

	@Test
	public void buscarLibros_DeberiaRetornarLista() throws Exception {
		// Configurar Mock para el repositorio
		Mockito.when(libroRepositorio.buscarPorTitulo(anyString()))
				.thenReturn(Arrays.asList(new Libro("El Quijote", "Cervantes", 500, "Novela", true)));

		// Ejecutar la prueba
		mockMvc.perform(get("/libros/buscar?titulo=quijote")).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray()) // Verifica que hay un array "content"
				.andExpect(jsonPath("$.content.length()").value(1)) // Verifica que tiene 1 elemento
				.andExpect(jsonPath("$[0].titulo").value("El Quijote")); // Verifica el título
	}
}
