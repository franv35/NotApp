package com.imb2025.notapp.controller;

import com.imb2025.notapp.entity.Usuario;
import com.imb2025.notapp.entity.dto.LoginUserDTO;
import com.imb2025.notapp.entity.dto.RegisterUserDTO;
import com.imb2025.notapp.service.IUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody RegisterUserDTO dto) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginUserDTO dto) {
        return ResponseEntity.ok(usuarioService.login(dto));
    }
}
