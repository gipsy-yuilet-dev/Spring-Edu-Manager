package com.springedumanager.dto;

import com.springedumanager.model.Estudiante;

/**
 * DTO de salida para respuestas de estudiantes.
 *
 * @param id identificador del estudiante
 * @param nombre nombre del estudiante
 * @param email correo del estudiante
 * @param cursoId id del curso asociado
 * @param cursoNombre nombre del curso asociado
 */
public record EstudianteResponse(Long id, String nombre, String email, Long cursoId, String cursoNombre) {

    /**
     * Convierte una entidad {@link Estudiante} en su representacion de salida.
     *
     * @param estudiante entidad de origen
     * @return DTO de salida
     */
    public static EstudianteResponse fromEntity(Estudiante estudiante) {
        Long cursoId = estudiante.getCurso() != null ? estudiante.getCurso().getId() : null;
        String cursoNombre = estudiante.getCurso() != null ? estudiante.getCurso().getNombre() : null;

        return new EstudianteResponse(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getEmail(),
                cursoId,
                cursoNombre
        );
    }
}
