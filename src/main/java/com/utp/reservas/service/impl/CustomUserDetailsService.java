package com.utp.reservas.service.impl;

import com.utp.reservas.model.entity.Usuario;
import com.utp.reservas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método que carga el usuario por nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca al usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Construye el objeto UserDetails utilizando la clase User de Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername()) // Establece el nombre de usuario
                .password(usuario.getPassword()) // Establece la contraseña encriptada
                .roles(usuario.getRol().getNombre().toUpperCase()) // Establece los roles (en mayúsculas)
                .build();
    }
}
