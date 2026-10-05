package com.academydavidgil.academydavid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
        contactoService.guardarMensaje(contacto);
        return "redirect:/contacto?enviado";
    }
}
