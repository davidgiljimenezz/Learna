package com.academydavidgil.academydavid.controller;

import java.security.Principal;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.academydavidgil.academydavid.service.CursoService;

@Controller
public class CursoController {
    private final CursoService cursos;
    public CursoController(CursoService cursos) { this.cursos = cursos; }

    @GetMapping("/cursos")
    public String catalogo(Model model) {
        model.addAttribute("cursos", cursos.catalogo());
        return "cursos";
    }

    @GetMapping("/cursos/{id}")
    public String detalle(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("curso", cursos.obtenerPublicado(id));
        model.addAttribute("inscrito", principal != null && cursos.misCursos(principal.getName()).stream()
                .anyMatch(i -> i.getCurso().getId().equals(id)));
        return "curso";
    }

    @PostMapping("/cursos/{id}/inscripcion")
    public String inscribir(@PathVariable Long id, Principal principal, RedirectAttributes redirect) {
        try {
            boolean nueva = cursos.inscribir(id, principal.getName());
            redirect.addFlashAttribute("mensaje", nueva ? "¡Inscripción completada!" : "Ya estás inscrito en este curso.");
        } catch (DataIntegrityViolationException ex) {
            redirect.addFlashAttribute("mensaje", "No se pudo completar la inscripción. Comprueba tus cursos e inténtalo de nuevo.");
        }
        return "redirect:/mis-cursos";
    }

    @GetMapping("/mis-cursos")
    public String misCursos(Principal principal, Model model) {
        model.addAttribute("inscripciones", cursos.misCursos(principal.getName()));
        return "mis-cursos";
    }
}
