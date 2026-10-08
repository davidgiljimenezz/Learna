package com.academydavidgil.academydavid.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "inscripciones", uniqueConstraints = @UniqueConstraint(name = "uk_inscripcion_usuario_curso", columnNames = {"usuario_id", "curso_id"}))
public class Inscripcion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    @Column(nullable = false)
    private LocalDateTime fecha;

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
