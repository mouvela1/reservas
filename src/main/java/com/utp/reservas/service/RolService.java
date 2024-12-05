package com.utp.reservas.service;

import com.utp.reservas.model.entity.Rol;
import com.utp.reservas.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    private final RoleRepository rolRepository;

    @Autowired
    public RolService(RoleRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll(); // Obtiene todos los registros de la tabla `rol`
    }

    public String obtenerNombreRol(Long idRol) {
        // Busca el rol en la base de datos y devuelve el nombre
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado para el ID: " + idRol));
        return rol.getNombre();
    }
}
