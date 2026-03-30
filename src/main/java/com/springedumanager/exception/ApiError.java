package com.springedumanager.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Estructura estandar de error para respuestas REST.
 *
 * @param timestamp fecha y hora del error
 * @param status codigo HTTP
 * @param error nombre de estado HTTP
 * @param message mensaje general del error
 * @param details lista de detalles especificos
 * @param path ruta donde ocurrio el error
 */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<String> details,
        String path
) {
}
