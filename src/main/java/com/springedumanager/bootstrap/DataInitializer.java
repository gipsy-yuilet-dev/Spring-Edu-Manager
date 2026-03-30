package com.springedumanager.bootstrap;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.springedumanager.model.Curso;
import com.springedumanager.model.Estudiante;
import com.springedumanager.service.CursoService;
import com.springedumanager.service.EstudianteService;

@Component
/**
 * Inicializa datos de ejemplo al arrancar la aplicacion cuando la base esta vacia.
 */
public class DataInitializer implements CommandLineRunner {

    private final CursoService cursoService;
    private final EstudianteService estudianteService;

    public DataInitializer(CursoService cursoService, EstudianteService estudianteService) {
        this.cursoService = cursoService;
        this.estudianteService = estudianteService;
    }

    @Override
    public void run(String... args) {
        if (cursoService.contar() == 0) {
            Curso java = new Curso();
            java.setNombre("Java Backend");
            java.setDescripcion("Desarrollo de APIs con Spring Boot");

            Curso web = new Curso();
            web.setNombre("Frontend Web");
            web.setDescripcion("Interfaces modernas con HTML, CSS y Bootstrap");

            cursoService.guardarTodos(List.of(java, web));
        }

        if (estudianteService.contar() == 0) {
            List<Curso> cursos = cursoService.listarTodos();
            Long cursoJavaId = cursos.isEmpty() ? null : cursos.get(0).getId();
            Long cursoWebId = cursos.size() > 1 ? cursos.get(1).getId() : cursoJavaId;

            Estudiante ana = new Estudiante();
            ana.setNombre("Ana Torres");
            ana.setEmail("ana.torres@example.com");

            Estudiante carlos = new Estudiante();
            carlos.setNombre("Carlos Silva");
            carlos.setEmail("carlos.silva@example.com");

            estudianteService.guardar(ana, cursoJavaId);
            estudianteService.guardar(carlos, cursoWebId);
        }
    }
}
