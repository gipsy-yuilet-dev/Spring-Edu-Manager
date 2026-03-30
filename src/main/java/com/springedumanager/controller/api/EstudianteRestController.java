package com.springedumanager.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springedumanager.dto.EstudianteRequest;
import com.springedumanager.dto.EstudianteResponse;
import com.springedumanager.model.Estudiante;
import com.springedumanager.service.EstudianteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estudiantes")
/**
 * API REST para operaciones CRUD de estudiantes.
 */
public class EstudianteRestController {

    private final EstudianteService estudianteService;

    public EstudianteRestController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<EstudianteResponse> listar() {
        return estudianteService.listarTodos().stream()
                .map(EstudianteResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponse> buscarPorId(@PathVariable Long id) {
        return estudianteService.buscarOpcionalPorId(id)
                .map(EstudianteResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstudianteResponse> crear(@Valid @RequestBody EstudianteRequest request) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(request.nombre());
        estudiante.setEmail(request.email());

        Estudiante creado = estudianteService.guardar(estudiante, request.cursoId());
        return ResponseEntity.status(HttpStatus.CREATED).body(EstudianteResponse.fromEntity(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponse> actualizar(@PathVariable Long id, @Valid @RequestBody EstudianteRequest request) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(request.nombre());
        estudiante.setEmail(request.email());

        Estudiante actualizado = estudianteService.actualizar(id, estudiante, request.cursoId());
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EstudianteResponse.fromEntity(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = estudianteService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
