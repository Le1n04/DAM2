package biblioteca.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import biblioteca.modelos.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

	@Query(value = "SELECT * FROM libros WHERE titulo LIKE CONCAT('%', :titulo, '%')", nativeQuery = true)
	List<Libro> buscarPorTitulo(@Param("titulo") String titulo);


}
