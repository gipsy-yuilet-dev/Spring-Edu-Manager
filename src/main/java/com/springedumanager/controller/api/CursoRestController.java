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

import com.springedumanager.dto.CursoRequest;
import com.springedumanager.dto.CursoResponse;
import com.springedumanager.model.Curso;
import com.springedumanager.service.CursoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cursos")
/**
 * API REST para operaciones CRUD de cursos.
 */
public class CursoRestController {

    private final CursoService cursoService;

    public CursoRestController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<CursoResponse> listar() {
        return cursoService.listarTodos().stream()
                .map(CursoResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable Long id) {
        return cursoService.buscarOpcionalPorId(id)
                .map(CursoResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CursoResponse> crear(@Valid @RequestBody CursoRequest request) {
        Curso curso = new Curso();
        curso.setNombre(request.nombre());
        curso.setDescripcion(request.descripcion());

        Curso creado = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(CursoResponse.fromEntity(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        Curso curso = new Curso();
        curso.setNombre(request.nombre());
        curso.setDescripcion(request.descripcion());

        Curso actualizado = cursoService.actualizar(id, curso);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CursoResponse.fromEntity(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = cursoService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
