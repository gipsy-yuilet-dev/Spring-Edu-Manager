package com.springedumanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
/**
 * Controlador de navegacion general para vistas comunes.
 */
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/estudiantes";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado(Model model) {
        model.addAttribute("mensaje", "No tienes permisos para acceder a este recurso.");
        return "acceso-denegado";
    }

    @GetMapping("/interoperabilidad")
    public String interoperabilidad() {
        return "api-demo";
    }
}
