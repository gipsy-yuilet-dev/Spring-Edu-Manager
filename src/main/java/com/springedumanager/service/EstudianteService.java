package com.springedumanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springedumanager.model.Curso;
import com.springedumanager.model.Estudiante;
import com.springedumanager.repository.CursoRepository;
import com.springedumanager.repository.EstudianteRepository;

@Service
/**
 * Servicio de negocio para operaciones sobre estudiantes.
 */
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public EstudianteService(EstudianteRepository estudianteRepository, CursoRepository cursoRepository) {
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     * Obtiene todos los estudiantes registrados.
     *
     * @return lista de estudiantes
     */
    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    /**
     * Obtiene estudiantes paginados para vistas de listado.
     *
     * @param pageable configuracion de pagina y tamanio
     * @return pagina de estudiantes
     */
    public Page<Estudiante> listarPaginado(Pageable pageable) {
        return estudianteRepository.findAll(pageable);
    }

    public long contar() {
        return estudianteRepository.count();
    }

    /**
     * Crea o actualiza un estudiante y opcionalmente lo asocia a un curso.
     *
     * @param estudiante entidad a persistir
     * @param cursoId identificador del curso asociado, puede ser null
     * @return estudiante guardado
     */
    public Estudiante guardar(Estudiante estudiante, Long cursoId) {
        asignarCurso(estudiante, cursoId);
        return estudianteRepository.save(estudiante);
    }

    public List<Estudiante> guardarTodos(List<Estudiante> estudiantes) {
        return estudianteRepository.saveAll(estudiantes);
    }

    public Optional<Estudiante> buscarOpcionalPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public Estudiante actualizar(Long id, Estudiante datos, Long cursoId) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudiante.setNombre(datos.getNombre());
                    estudiante.setEmail(datos.getEmail());
                    asignarCurso(estudiante, cursoId);
                    return estudianteRepository.save(estudiante);
                })
                .orElse(null);
    }

    public boolean eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            return false;
        }
        estudianteRepository.deleteById(id);
        return true;
    }

    private void asignarCurso(Estudiante estudiante, Long cursoId) {
        if (cursoId == null) {
            estudiante.setCurso(null);
            return;
        }

        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        estudiante.setCurso(curso);
    }
}
