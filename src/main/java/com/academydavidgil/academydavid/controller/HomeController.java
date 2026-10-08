package com.academydavidgil.academydavid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.academydavidgil.academydavid.service.CursoService;

@Controller
public class HomeController {
    private final CursoService cursos;
        public HomeController(CursoService cursos) { this.cursos = cursos; }


    @GetMapping({"/", "/index"})
    public String mostrarIndex(Model model) {
        model.addAttribute("cursos", cursos.catalogo());
        return "index";
    }

}
