package com.utp.reservas.service.impl;

import com.utp.reservas.model.entity.Usuario;
import com.utp.reservas.model.entity.Rol;
import com.utp.reservas.factory.UsuarioFactory;
import com.utp.reservas.repository.UsuarioRepository;
import com.utp.reservas.repository.RoleRepository;
import com.utp.reservas.service.UserService;
import com.utp.reservas.util.PasswordEncoderUtil;
import com.utp.reservas.util.UsuarioUtil;
import com.utp.reservas.util.JwtUtil;
import com.utp.reservas.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    private RoleRepository rolRepository;

    @Override
    public Optional<Usuario> login(String username, String password) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsernameWithRol(username);
        if (userOpt.isPresent()) {
            Usuario usuario = userOpt.get();
            if (passwordEncoderUtil.matches(password, usuario.getPassword())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    @Override
    public String generarToken(Usuario usuario) {
        String nombreRol = usuario.getRol().getNombre();
        return JwtUtil.generarToken(usuario.getUsername(), nombreRol);
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {

        String CodigoEstudiante = UsuarioUtil.generarCodigoEstudiante(usuario.getDni(), usuario.getNombre(), usuario.getApellido());
        String Correo = UsuarioUtil.generarCorreo(CodigoEstudiante);

        usuario.setCorreo(Correo);
        usuario.setCodigoEstudiante(CodigoEstudiante);

        // Verifica duplicados
        if (usuarioRepository.findByDni(usuario.getDni()).isPresent()) {
            throw new RuntimeException("El DNI ya está registrado");
        }
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está registrado");
        }

        // Busca o asigna el rol como en la solución anterior
        Rol rolAsignado = obtenerRol(usuario);

        // Crea el usuario usando el rol asignado
        Usuario nuevoUsuario = UsuarioFactory.crearUsuario(
                usuario.getDni(),
                usuario.getNombre(),
                usuario.getApellido(),
                Correo,
                CodigoEstudiante,
                usuario.getUsername(),
                passwordEncoderUtil.encode(usuario.getPassword()),
                rolAsignado
        );

        return usuarioRepository.save(nuevoUsuario);
    }

    private Rol obtenerRol(Usuario usuario) {
        if (usuario.getRol() != null && usuario.getRol().getId() != null) {
            return rolRepository.findById(usuario.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("El rol proporcionado no existe"));
        }
        return rolRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
    }


    public Optional<UsuarioDTO> obtenerUsuarioPorDni(String dni) {
        return usuarioRepository.findByDni(dni)
                .map(usuario -> new UsuarioDTO(
                        usuario.getUsername(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getCorreo(),
                        usuario.getRol().getNombre()
                ));
    }

    public Optional<UsuarioDTO> obtenerUsuarioPorCodigoEstudiante(String codigoEstudiante) {
        return usuarioRepository.findByCodigoEstudiante(codigoEstudiante)
                .map(usuario -> new UsuarioDTO(
                        usuario.getUsername(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getCorreo(),
                        usuario.getRol().getNombre()
                ));
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getUsername(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getCorreo(),
                        usuario.getRol().getNombre() // Accede al nombre del rol
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

}


