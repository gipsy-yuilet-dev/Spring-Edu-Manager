package com.springedumanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springedumanager.model.Curso;
import com.springedumanager.repository.CursoRepository;

@Service
/**
 * Servicio de negocio para operaciones sobre cursos.
 */
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    /**
     * Obtiene todos los cursos registrados.
     *
     * @return lista de cursos
     */
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public long contar() {
        return cursoRepository.count();
    }

    /**
     * Crea o actualiza un curso.
     *
     * @param curso entidad a persistir
     * @return curso guardado
     */
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> guardarTodos(List<Curso> cursos) {
        return cursoRepository.saveAll(cursos);
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    public Optional<Curso> buscarOpcionalPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso actualizar(Long id, Curso datos) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNombre(datos.getNombre());
                    curso.setDescripcion(datos.getDescripcion());
                    return cursoRepository.save(curso);
                })
                .orElse(null);
    }

    public boolean eliminar(Long id) {
        if (!cursoRepository.existsById(id)) {
            return false;
        }
        cursoRepository.deleteById(id);
        return true;
    }
}
