package com.academydavidgil.academydavid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.academydavidgil.academydavid.model.Contacto;
import com.academydavidgil.academydavid.service.ContactoService;

import jakarta.validation.Valid;


@Controller
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @InitBinder("contacto")
    void camposPermitidos(WebDataBinder binder) {
        binder.setAllowedFields("nombre", "email", "asunto", "mensaje");
    }

    @GetMapping("/contacto")
    public String mostrarContacto(org.springframework.ui.Model model) {
        model.addAttribute("contacto", new Contacto());
        return "contacto";
    }

    @PostMapping("/contacto")
    public String enviarContacto(@Valid @ModelAttribute Contacto contacto , BindingResult result) {
        if (result.hasErrors()) {
            return "contacto";
        }
        try {
            contactoService.guardarMensaje(contacto);
        } catch (DataAccessException ex) {
            result.reject("contacto", "No se pudo enviar el mensaje. Inténtalo de nuevo más tarde.");
            return "contacto";
        }
        return "redirect:/contacto?enviado";
    }
}
