package com.springedumanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear o actualizar estudiantes via API REST.
 *
 * @param nombre nombre del estudiante
 * @param email correo electronico del estudiante
 * @param cursoId id del curso asociado, puede ser null
 */
public record EstudianteRequest(
	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
	String nombre,
	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email no tiene un formato valido")
	@Size(max = 150, message = "El email no puede exceder 150 caracteres")
	String email,
	Long cursoId
) {
}
