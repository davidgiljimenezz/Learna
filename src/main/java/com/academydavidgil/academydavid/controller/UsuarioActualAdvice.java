package com.academydavidgil.academydavid.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UsuarioActualAdvice {
    @ModelAttribute("usuarioActual")
    public String usuarioActual(Principal principal) {
        return principal == null ? null : principal.getName();
    }
}
