package com.academydavidgil.academydavid.service;

import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.academydavidgil.academydavid.dto.RegistroForm;
import com.academydavidgil.academydavid.model.Usuario;
import com.academydavidgil.academydavid.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarios, PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.encoder = encoder;
    }

    public Usuario registrar(RegistroForm form) {
        String email = normalizarEmail(form.getEmail());
        if (usuarios.existsByEmail(email)) {
            throw new EmailDuplicadoException();
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(form.getNombre().strip());
        usuario.setEmail(email);
        usuario.setPasswordHash(encoder.encode(form.getPassword()));
        return usuarios.saveAndFlush(usuario);
    }

    public static String normalizarEmail(String email) {
        return email.strip().toLowerCase(Locale.ROOT);
    }

    public static class EmailDuplicadoException extends RuntimeException { }
}
