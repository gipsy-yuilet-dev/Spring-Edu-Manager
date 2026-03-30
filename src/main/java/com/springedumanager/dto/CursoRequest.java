package com.springedumanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear o actualizar cursos via API REST.
 *
 * @param nombre nombre del curso
 * @param descripcion descripcion breve del curso
 */
public record CursoRequest(
	@NotBlank(message = "El nombre del curso es obligatorio")
	@Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
	String nombre,
	@Size(max = 255, message = "La descripcion no puede exceder 255 caracteres")
	String descripcion
) {
}
