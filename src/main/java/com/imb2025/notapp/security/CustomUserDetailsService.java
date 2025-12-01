package com.imb2025.notapp.security;

import com.imb2025.notapp.entity.Usuario;
import com.imb2025.notapp.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Adaptamos nuestra entidad Usuario al modelo de Spring Security
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // ⚠️ debe estar encriptado con PasswordEncoder
                .roles("USER") // Podés extenderlo a múltiples roles si tu entidad lo soporta
                .build();
    }
}
