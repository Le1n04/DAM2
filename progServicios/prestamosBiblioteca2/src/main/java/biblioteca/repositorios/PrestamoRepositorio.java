package biblioteca.repositorios;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import biblioteca.modelos.Prestamo;

@Repository
public interface PrestamoRepositorio extends CrudRepository<Prestamo, Long> {
	// obtener prestamos por id de lector
	@Query("SELECT p FROM Prestamo p WHERE p.idLector = :idLector")
	List<Prestamo> findByIdLector(@Param("idLector") Long idLector);

	// obtener estadisticas de prestamos por grupo
	@Query(value = "SELECT l.cod_curso as codCurso, COUNT(*) as total FROM prestamos p JOIN lectores l ON p.id_lector = l.id GROUP BY l.cod_curso", nativeQuery = true)
	List<Map<String, Object>> obtenerEstadisticasPorGrupo();
}
