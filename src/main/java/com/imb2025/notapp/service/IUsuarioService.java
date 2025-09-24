package com.imb2025.notapp.service;

import com.imb2025.notapp.entity.Usuario;
import com.imb2025.notapp.entity.dto.LoginUserDTO;
import com.imb2025.notapp.entity.dto.RegisterUserDTO;

public interface IUsuarioService {
    String registrarUsuario(RegisterUserDTO dto);
    Usuario login(LoginUserDTO dto);
}
