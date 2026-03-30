package com.springedumanager.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springedumanager.model.Curso;
import com.springedumanager.service.CursoService;

@Controller
@RequestMapping("/cursos")
/**
 * Controlador MVC para gestion de cursos en vistas Thymeleaf.
 */
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
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

        Page<Curso> cursosPage = cursoService.listarPaginado(PageRequest.of(safePage, safeSize));

        model.addAttribute("cursos", cursosPage.getContent());
        model.addAttribute("currentPage", safePage);
        model.addAttribute("pageSize", safeSize);
        model.addAttribute("totalPages", cursosPage.getTotalPages());
        model.addAttribute("hasPrevious", cursosPage.hasPrevious());
        model.addAttribute("hasNext", cursosPage.hasNext());
        return "cursos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("curso", new Curso());
        return "cursos/form";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Curso curso = cursoService.buscarPorId(id);
        if (curso == null) {
            return "redirect:/cursos";
        }

        model.addAttribute("curso", curso);
        return "cursos/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Curso curso) {
        cursoService.guardar(curso);
        return "redirect:/cursos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return "redirect:/cursos";
    }
}
