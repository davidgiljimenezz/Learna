package com.academydavidgil.academydavid.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academydavidgil.academydavid.model.Contacto;
import com.academydavidgil.academydavid.repository.ContactoRepository;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    public void guardarMensaje(Contacto contacto) {
        contacto.setFecha(LocalDateTime.now());
        contacto.setLeido(false);
        contactoRepository.save(contacto);
    }
}
