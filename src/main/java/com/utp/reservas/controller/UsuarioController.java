package com.utp.reservas.controller;


import com.utp.reservas.model.dto.LoginResponse;
import com.utp.reservas.model.entity.Usuario;
import com.utp.reservas.service.UserService;
import com.utp.reservas.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UserService userService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = userService.registrarUsuario(usuario);
            // Devuelve únicamente el código de usuario (username)
            return ResponseEntity.status(HttpStatus.CREATED).body("Exito, se a creado un nuevo usuario con codigo de Usuario: " + nuevoUsuario.getCodigoEstudiante());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Usuario usuario) {
        Optional<Usuario> userOpt = userService.login(usuario.getUsername(), usuario.getPassword());
        if (userOpt.isPresent()) {

            Usuario UsuarioLogueado = userOpt.get();
            System.out.println(UsuarioLogueado); // Depuración en consola
            String token = userService.generarToken(UsuarioLogueado);

            // Crear la respuesta
            LoginResponse response = new LoginResponse(
                    UsuarioLogueado.getUsername(),
                    "Bearer "+token,
                    UsuarioLogueado.getNombre() + " " + UsuarioLogueado.getApellido()
            );

            // Devolver el objeto como JSON
            return ResponseEntity.ok(response);
        }

        // Respuesta en caso de error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @GetMapping("/dni/{dni}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorDni(@PathVariable String dni) {
        return userService.obtenerUsuarioPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/codigoStudiante/{codigo}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorCodigo(@PathVariable String codigo) {
        return userService.obtenerUsuarioPorCodigoEstudiante(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATIVO', 'ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = userService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return userService.actualizarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public void eliminarUsuario(@PathVariable Long id) {
        userService.eliminarUsuario(id);
    }
}
