package com.utp.reservas.controller;

import com.utp.reservas.dto.ReservaDTO;
import com.utp.reservas.model.entity.Reserva;
import com.utp.reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'PROFESOR', 'ADMINISTRATIVO', 'ADMIN')")
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Reserva nuevaReserva = reservaService.crearReserva(reserva, username);
        return ResponseEntity.ok(nuevaReserva);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'PROFESOR', 'ADMINISTRATIVO', 'ADMIN')")
    public ResponseEntity<List<ReservaDTO>> listarTodasReservas() {
        return ResponseEntity.ok(reservaService.listarTodasReservas());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'PROFESOR', 'ADMINISTRATIVO', 'ADMIN')")
    public ResponseEntity<List<ReservaDTO>> listarReservasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.listarReservasPorUsuario(usuarioId));
    }

    @PutMapping("/{reservaId}/confirmar")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public Reserva confirmarReserva(@PathVariable Long reservaId) {
        return reservaService.confirmarReserva(reservaId);
    }

    @PutMapping("/{reservaId}/cancelar")
    @PreAuthorize("hasRole('ADMINISTRATIVO') or hasRole('ESTUDIANTE')")
    public Reserva cancelarReserva(@PathVariable Long reservaId) {
        return reservaService.cancelarReserva(reservaId);
    }
}


