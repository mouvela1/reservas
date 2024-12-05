package com.utp.reservas.factory;

import com.utp.reservas.model.entity.Rol;
import com.utp.reservas.model.entity.Usuario;

public class UsuarioFactory {

    public static Usuario crearUsuario(String dni, String nombre, String apellido, String correo, String codigoEstudiante, String username, String password, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreo(correo);
        usuario.setCodigoEstudiante(codigoEstudiante);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setRol(rol);
        return usuario;
    }

}
