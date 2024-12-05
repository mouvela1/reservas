package com.utp.reservas.service;

import com.utp.reservas.dto.UsuarioDTO;
import com.utp.reservas.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Usuario registrarUsuario(Usuario usuario);

    List<UsuarioDTO> listarUsuarios();

    Optional<UsuarioDTO> obtenerUsuarioPorDni(String id);

    Optional<UsuarioDTO> obtenerUsuarioPorCodigoEstudiante(String email);

    Usuario actualizarUsuario(Usuario usuario);

    void eliminarUsuario(Long id);

    Optional<Usuario> login(String username, String password);

    String generarToken(Usuario usuario);
}
