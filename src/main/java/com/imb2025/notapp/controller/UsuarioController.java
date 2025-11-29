package com.imb2025.notapp.controller;

import com.imb2025.notapp.entity.Usuario;
import com.imb2025.notapp.entity.dto.LoginUserDTO;
import com.imb2025.notapp.entity.dto.RegisterUserDTO;
import com.imb2025.notapp.service.IUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    public UsuarioController(IUsuarioService usuarioService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
    }

    // ✅ Registro de usuario con contraseña encriptada
    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody RegisterUserDTO dto) {
        usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    // ✅ Login usando AuthenticationManager
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok("Login exitoso");
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    // ✅ Obtener el usuario actual loggeado
    @GetMapping("/me")
    public ResponseEntity<Usuario> getCurrentUser(Principal principal) {
        Usuario usuario = usuarioService.findByUsername(principal.getName());
        return ResponseEntity.ok(usuario);
    }
}
