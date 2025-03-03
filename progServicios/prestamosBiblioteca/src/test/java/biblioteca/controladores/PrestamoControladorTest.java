package biblioteca.controladores;

import biblioteca.repositorios.PrestamoRepositorio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RestController
@RequestMapping("/api")
public class PrestamoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoRepositorio prestamoRepositorio;

    @Test
    public void obtenerEstadisticas_DeberiaRetornarDatos() throws Exception {
        List<Map<String, Object>> datos = List.of(Map.of("codCurso", "DAM1", "total", 5));

        Mockito.when(prestamoRepositorio.obtenerEstadisticasPorGrupo()).thenReturn(datos);

        mockMvc.perform(get("/prestamos/estadisticas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codCurso").value("DAM1"))
                .andExpect(jsonPath("$[0].total").value(5));
    }
}
