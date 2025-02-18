package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Lector;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
}
