package com.imb2025.notapp.service;

import com.imb2025.notapp.entity.Usuario;
import com.imb2025.notapp.entity.dto.LoginUserDTO;
import com.imb2025.notapp.entity.dto.RegisterUserDTO;
import com.imb2025.notapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImp implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public String registrarUsuario(RegisterUserDTO dto) {
        Optional<Usuario> existente = usuarioRepository.findByUsername(dto.getUsername());
        if (existente.isPresent()) throw new RuntimeException("El usuario ya existe");

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword()); // ⚠️ En producción, usar hash
        usuario.setNombreCompleto(dto.getNombreCompleto());

        usuarioRepository.save(usuario);
        return "Usuario registrado correctamente";
    }

    @Override
    public Usuario login(LoginUserDTO dto) {
        Usuario usuario = usuarioRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }
}
