package com.imb2025.notapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ✅ Encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ AuthenticationManager para login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // ✅ Configuración de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // deshabilitamos CSRF para simplificar
                .authorizeHttpRequests(auth -> auth
                        // ✅ Permitimos acceso a archivos estáticos
                        .requestMatchers(
                                "/login.html", "/register.html",
                                "/new.css", "/styles.css",
                                "/login.js", "/register.js", "/script.js",
                                "/img/**"
                        ).permitAll()
                        // ✅ Permitimos acceso a endpoints públicos
                        .requestMatchers("/usuarios/register", "/usuarios/login").permitAll()
                        // ✅ Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // ✅ archivo estático como página de login
                        .loginProcessingUrl("/login") // ✅ Spring Security procesa el POST aquí
                        .defaultSuccessUrl("/", true) // ✅ redirige al home tras login exitoso
                        .failureUrl("/login.html?error=true") // ✅ redirige con parámetro si falla
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/usuarios/logout")
                        .logoutSuccessUrl("/login.html?logout=true") // ✅ redirige tras logout
                        .permitAll()
                );

        return http.build();
    }
}
