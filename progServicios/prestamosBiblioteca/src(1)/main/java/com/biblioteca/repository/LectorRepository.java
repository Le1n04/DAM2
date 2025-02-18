package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Lector;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
}
