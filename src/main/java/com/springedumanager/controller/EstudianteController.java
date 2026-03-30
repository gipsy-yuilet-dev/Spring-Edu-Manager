package com.springedumanager.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springedumanager.model.Estudiante;
import com.springedumanager.service.CursoService;
import com.springedumanager.service.EstudianteService;

@Controller
@RequestMapping("/estudiantes")
/**
 * Controlador MVC para gestion de estudiantes en vistas Thymeleaf.
 */
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public EstudianteController(EstudianteService estudianteService, CursoService cursoService) {
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(size, 1);

        Page<Estudiante> estudiantesPage = estudianteService.listarPaginado(PageRequest.of(safePage, safeSize));

        model.addAttribute("estudiantes", estudiantesPage.getContent());
        model.addAttribute("currentPage", safePage);
        model.addAttribute("pageSize", safeSize);
        model.addAttribute("totalPages", estudiantesPage.getTotalPages());
        model.addAttribute("hasPrevious", estudiantesPage.hasPrevious());
        model.addAttribute("hasNext", estudiantesPage.hasNext());
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("cursos", cursoService.listarTodos());
        return "estudiantes/form";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Estudiante estudiante = estudianteService.buscarOpcionalPorId(id).orElse(null);
        if (estudiante == null) {
            return "redirect:/estudiantes";
        }

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("cursos", cursoService.listarTodos());
        return "estudiantes/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Estudiante estudiante, @RequestParam(value = "cursoId", required = false) Long cursoId) {
        estudianteService.guardar(estudiante, cursoId);
        return "redirect:/estudiantes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return "redirect:/estudiantes";
    }
}
