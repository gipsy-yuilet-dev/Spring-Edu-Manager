package com.springedumanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springedumanager.model.Curso;

@Repository
/**
 * Repositorio de acceso a datos para la entidad {@link Curso}.
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
