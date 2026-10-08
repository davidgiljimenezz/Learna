package com.academydavidgil.academydavid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.academydavidgil.academydavid.repository.UsuarioRepository;
import com.academydavidgil.academydavid.service.UsuarioService;

@Configuration
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    UserDetailsService userDetailsService(UsuarioRepository usuarios) {
        return email -> usuarios.findByEmail(UsuarioService.normalizarEmail(email))
                .map(usuario -> User.withUsername(usuario.getEmail())
                        .password(usuario.getPasswordHash()).roles("ALUMNO").build())
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales incorrectas"));
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/login", "/registro", "/contacto", "/error",
                                "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/cursos", "/cursos/*").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").usernameParameter("email")
                        .defaultSuccessUrl("/mis-cursos", false).failureUrl("/login?error").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?salida").permitAll())
                .build();
    }
}
