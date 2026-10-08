package com.academydavidgil.academydavid.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.academydavidgil.academydavid.model.Curso;
import com.academydavidgil.academydavid.model.Inscripcion;
import com.academydavidgil.academydavid.model.Usuario;
import com.academydavidgil.academydavid.repository.CursoRepository;
import com.academydavidgil.academydavid.repository.InscripcionRepository;
import com.academydavidgil.academydavid.repository.UsuarioRepository;

@Service
public class CursoService {
    private final CursoRepository cursos;
    private final UsuarioRepository usuarios;
    private final InscripcionRepository inscripciones;

    public CursoService(CursoRepository cursos, UsuarioRepository usuarios, InscripcionRepository inscripciones) {
        this.cursos = cursos;
        this.usuarios = usuarios;
        this.inscripciones = inscripciones;
    }

    public List<Curso> catalogo() { return cursos.findByPublicadoTrueOrderByTituloAsc(); }

    public Curso obtenerPublicado(Long id) {
        return cursos.findByIdAndPublicadoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no disponible"));
    }

    private Usuario alumno(String email) {
        return usuarios.findByEmail(UsuarioService.normalizarEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    @Transactional(readOnly = true)
    public List<Inscripcion> misCursos(String email) {
        return inscripciones.findByUsuarioIdOrderByFechaDesc(alumno(email).getId());
    }

    @Transactional
    public boolean inscribir(Long cursoId, String email) {
        Curso curso = obtenerPublicado(cursoId);
        Usuario usuario = alumno(email);
        if (inscripciones.existsByUsuarioIdAndCursoId(usuario.getId(), cursoId)) { return false; }
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setCurso(curso);
        inscripcion.setFecha(LocalDateTime.now());
        inscripciones.saveAndFlush(inscripcion);
        return true;
    }
}
