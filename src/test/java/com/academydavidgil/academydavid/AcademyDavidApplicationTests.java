package com.academydavidgil.academydavid;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import com.academydavidgil.academydavid.model.*;
import com.academydavidgil.academydavid.repository.*;
import com.academydavidgil.academydavid.dto.RegistroForm;
import com.academydavidgil.academydavid.service.UsuarioService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
class AcademyDavidApplicationTests {
    @Autowired MockMvc mvc;
    @Autowired UsuarioRepository usuarios;
    @Autowired CursoRepository cursos;
    @Autowired InscripcionRepository inscripciones;
    @Autowired ContactoRepository contactos;
    @Autowired UsuarioService usuarioService;
    @Autowired PasswordEncoder encoder;

    @BeforeEach
    void limpiar() {
        inscripciones.deleteAll();
        usuarios.deleteAll();
        cursos.deleteAll();
        contactos.deleteAll();
    }

    @Test
    void paginasPublicasSeRenderizan() throws Exception {
        for (String ruta : new String[]{"/", "/login", "/contacto", "/cursos"}) {
            mvc.perform(get(ruta)).andExpect(status().isOk());
        }
    }

    @Test
    void registroGuardaHashYNormalizaEmail() throws Exception {
        mvc.perform(post("/registro").with(csrf()).param("nombre", " Ana ")
                .param("email", "ANA@example.com").param("password", "UnaClaveSegura123"))
                .andExpect(redirectedUrl("/login?registrado"));
        Usuario usuario = usuarios.findByEmail("ana@example.com").orElseThrow();
        assertThat(usuario.getNombre()).isEqualTo("Ana");
        assertThat(usuario.getPasswordHash()).isNotEqualTo("UnaClaveSegura123");
        assertThat(encoder.matches("UnaClaveSegura123", usuario.getPasswordHash())).isTrue();
    }

    @Test
    void registroRechazaDatosInvalidosYEmailDuplicado() throws Exception {
        mvc.perform(post("/registro").with(csrf()).param("nombre", " ")
                .param("email", "incorrecto").param("password", "corta"))
                .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("registro", "nombre", "email", "password"));
        assertThat(usuarios.count()).isZero();
        registrar("ana@example.com");
        mvc.perform(post("/registro").with(csrf()).param("nombre", "Otra Ana")
                .param("email", "ANA@example.com").param("password", "UnaClaveSegura123"))
                .andExpect(model().attributeHasFieldErrors("registro", "email"));
        assertThat(usuarios.count()).isEqualTo(1);
    }

    @Test
    void loginValidaCredencialesYCierraSesion() throws Exception {
        registrar("ana@example.com");
        mvc.perform(post("/login").with(csrf()).param("email", "ana@example.com").param("password", "incorrecta"))
                .andExpect(redirectedUrl("/login?error"));
        MockHttpSession session = (MockHttpSession) mvc.perform(post("/login").with(csrf())
                .param("email", "ANA@example.com").param("password", "UnaClaveSegura123"))
                .andExpect(redirectedUrl("/mis-cursos")).andReturn().getRequest().getSession(false);
        mvc.perform(get("/mis-cursos").session(session)).andExpect(status().isOk());
        mvc.perform(post("/logout").session(session).with(csrf())).andExpect(redirectedUrl("/login?salida"));
        assertThat(session.isInvalid()).isTrue();
    }

    @Test
    void rutasPrivadasYCsrfEstanProtegidos() throws Exception {
        mvc.perform(get("/mis-cursos")).andExpect(status().is3xxRedirection());
        mvc.perform(post("/registro").param("nombre", "Ana")).andExpect(status().isForbidden());
        mvc.perform(post("/cursos/1/inscripcion").with(csrf())).andExpect(status().is3xxRedirection());
        mvc.perform(post("/cursos/1/inscripcion").with(user("ana@example.com"))).andExpect(status().isForbidden());
    }

    @Test
    void catalogoSoloMuestraPublicadosYDetalleDevuelve404() throws Exception {
        Curso visible = crearCurso("Java inicial", true);
        crearCurso("Curso borrador", false);
        mvc.perform(get("/cursos")).andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Java inicial")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("Curso borrador"))));
        mvc.perform(get("/cursos/" + visible.getId())).andExpect(status().isOk());
        mvc.perform(get("/cursos/999999")).andExpect(status().isNotFound());
    }

    @Test
    void inscripcionNoSeDuplicaYNadieVeCursosAjenos() throws Exception {
        registrar("ana@example.com");
        registrar("otro@example.com");
        Curso curso = crearCurso("Java inicial", true);
        for (int intento = 0; intento < 2; intento++) {
            mvc.perform(post("/cursos/" + curso.getId() + "/inscripcion").with(csrf()).with(user("ana@example.com")))
                    .andExpect(redirectedUrl("/mis-cursos"));
        }
        assertThat(inscripciones.count()).isEqualTo(1);
        mvc.perform(get("/mis-cursos").with(user("ana@example.com")))
                .andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("Java inicial")));
        mvc.perform(get("/cursos/" + curso.getId()).with(user("ana@example.com"))).andExpect(status().isOk());
        mvc.perform(get("/mis-cursos").with(user("otro@example.com")))
                .andExpect(model().attribute("inscripciones", org.hamcrest.Matchers.empty()));
    }

    @Test
    void noSePuedeInscribirEnUnBorrador() throws Exception {
        registrar("ana@example.com");
        Curso borrador = crearCurso("Borrador", false);
        mvc.perform(post("/cursos/" + borrador.getId() + "/inscripcion").with(csrf()).with(user("ana@example.com")))
                .andExpect(status().isNotFound());
        assertThat(inscripciones.count()).isZero();
    }

    @Test
    void contactoValidaYGuardaMensaje() throws Exception {
        mvc.perform(post("/contacto").with(csrf()).param("nombre", " ").param("email", "incorrecto"))
                .andExpect(model().attributeHasErrors("contacto"));
        assertThat(contactos.count()).isZero();
        mvc.perform(post("/contacto").with(csrf()).param("nombre", "Ana").param("email", "ana@example.com")
                .param("asunto", "Información").param("mensaje", "Quiero aprender Java"))
                .andExpect(redirectedUrl("/contacto?enviado"));
        Contacto contacto = contactos.findAll().getFirst();
        assertThat(contacto.getFecha()).isNotNull();
        assertThat(contacto.isLeido()).isFalse();
    }

    private void registrar(String email) {
        RegistroForm form = new RegistroForm();
        form.setNombre("Ana"); form.setEmail(email); form.setPassword("UnaClaveSegura123");
        usuarioService.registrar(form);
    }

    @Test
    void contactoNoPermiteSobrescribirMensajesDesdeElFormulario() throws Exception {
        Contacto existente = new Contacto();
        existente.setNombre("Original"); existente.setEmail("original@example.com");
        existente.setAsunto("Original"); existente.setMensaje("Mensaje original");
        existente.setFecha(java.time.LocalDateTime.now());
        existente.setLeido(true);
        contactos.saveAndFlush(existente);
        mvc.perform(post("/contacto").with(csrf()).param("id", existente.getId().toString())
                .param("nombre", "Nuevo").param("email", "nuevo@example.com")
                .param("asunto", "Nuevo").param("mensaje", "Nuevo mensaje").param("leido", "true"))
                .andExpect(redirectedUrl("/contacto?enviado"));
        assertThat(contactos.count()).isEqualTo(2);
        assertThat(contactos.findById(existente.getId()).orElseThrow().getMensaje()).isEqualTo("Mensaje original");
    }

    @Test
    void contactoAdmiteMensajesLargosYRechazaLosExcesivos() throws Exception {
        mvc.perform(post("/contacto").with(csrf()).param("nombre", "Ana").param("email", "ana@example.com")
                .param("asunto", "Consulta").param("mensaje", "a".repeat(4000)))
                .andExpect(redirectedUrl("/contacto?enviado"));
        assertThat(contactos.findAll().getFirst().getMensaje()).hasSize(4000);
        mvc.perform(post("/contacto").with(csrf()).param("nombre", "Ana").param("email", "ana@example.com")
                .param("asunto", "Consulta").param("mensaje", "a".repeat(4001)))
                .andExpect(model().attributeHasFieldErrors("contacto", "mensaje"));
        assertThat(contactos.count()).isEqualTo(1);
    }

    private Curso crearCurso(String titulo, boolean publicado) {
        Curso curso = new Curso();
        curso.setTitulo(titulo); curso.setDescripcion("Aprende con ejercicios prácticos.");
        curso.setCategoria("Programación"); curso.setNivel("Inicial");
        curso.setDuracionHoras(20); curso.setPublicado(publicado);
        return cursos.saveAndFlush(curso);
    }
}
