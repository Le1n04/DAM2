package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    boolean existsByLectorIdAndFechaBajaIsNull(Long lectorId);
}
