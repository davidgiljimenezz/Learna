package com.academydavidgil.academydavid.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.academydavidgil.academydavid.dto.RegistroForm;
import com.academydavidgil.academydavid.service.UsuarioService;

@Controller
public class UsuarioController {
    private final UsuarioService usuarios;

    public UsuarioController(UsuarioService usuarios) { this.usuarios = usuarios; }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("registro", new RegistroForm());
        return "login";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("registro") RegistroForm registro,
                            BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                usuarios.registrar(registro);
                return "redirect:/login?registrado";
            } catch (UsuarioService.EmailDuplicadoException ex) {
                result.rejectValue("email", "duplicado", "Ya existe una cuenta con ese email");
            } catch (DataIntegrityViolationException ex) {
                // La restricción única también cubre registros simultáneos.
                result.reject("registro", "No se pudo crear la cuenta. Comprueba el email e inténtalo de nuevo.");
            }
        }
        registro.setPassword(null);
        model.addAttribute("mostrarRegistro", true);
        return "login";
    }
}
