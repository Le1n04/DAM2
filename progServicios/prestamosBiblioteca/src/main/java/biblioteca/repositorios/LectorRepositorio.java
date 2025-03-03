package biblioteca.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import biblioteca.modelos.Lector;

@Repository
public interface LectorRepositorio extends JpaRepository<Lector, Long> {

	@Query("SELECT l FROM Lector l WHERE LOWER(l.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
	List<Lector> buscarPorNombre(@Param("nombre") String nombre);

	List<Lector> findByNombreContainingIgnoreCase(String nombre);

}
