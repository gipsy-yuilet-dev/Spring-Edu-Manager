package com.springedumanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("cursos", cursoService.listarTodos());
        return "estudiantes/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Estudiante estudiante, @RequestParam(value = "cursoId", required = false) Long cursoId) {
        estudianteService.guardar(estudiante, cursoId);
        return "redirect:/estudiantes";
    }
}
