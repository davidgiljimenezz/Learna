package com.academydavidgil.academydavid.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contacto")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre admite hasta 100 caracteres")
    @Column(length = 100)
    private String nombre;
    @NotBlank (message = "El email es obligatorio")
    @Email(message= "Introduce un email válido")
    @Size(max = 254, message = "El email es demasiado largo")
    @Column(length = 254)
    private String email;
    @NotBlank (message = "El asunto es obligatorio")
    @Size(max = 200, message = "El asunto admite hasta 200 caracteres")
    @Column(length = 200)
    private String asunto;
    @NotBlank (message = "El mensaje es obligatorio")
    @Size(max = 4000, message = "El mensaje admite hasta 4000 caracteres")
    @Column(length = 4000)
    private String mensaje;

    private LocalDateTime fecha;
    private boolean leido;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public boolean isLeido() { return leido; }
    public void setLeido(boolean leido) { this.leido = leido; }
}
