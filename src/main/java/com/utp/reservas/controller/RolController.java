package com.utp.reservas.controller;

import com.utp.reservas.model.entity.Rol;
import com.utp.reservas.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<Rol> listarRoles() {
        return rolService.obtenerTodosLosRoles();
    }
}

