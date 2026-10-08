package com.academydavidgil.academydavid.dto;

import jakarta.validation.constraints.*;

public class RegistroForm {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre admite hasta 100 caracteres")
    private String nombre;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Introduce un email válido")
    @Size(max = 254, message = "El email es demasiado largo")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
    private String password;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email == null ? null : email.strip(); }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
