package com.springedumanager.dto;

import com.springedumanager.model.Curso;

/**
 * DTO de salida para representar cursos en respuestas JSON.
 *
 * @param id identificador del curso
 * @param nombre nombre del curso
 * @param descripcion descripcion del curso
 */
public record CursoResponse(Long id, String nombre, String descripcion) {

    /**
     * Convierte una entidad {@link Curso} en su representacion de salida.
     *
     * @param curso entidad de origen
     * @return DTO de salida
     */
    public static CursoResponse fromEntity(Curso curso) {
        return new CursoResponse(curso.getId(), curso.getNombre(), curso.getDescripcion());
    }
}
