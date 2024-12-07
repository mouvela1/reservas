package com.utp.reservas.service.impl;

import com.utp.reservas.dto.ReservaDTO;
import com.utp.reservas.mapper.ReservaMapper;
import com.utp.reservas.model.enums.EstadoReserva;
import com.utp.reservas.model.entity.Reserva;
import com.utp.reservas.model.entity.Space;
import com.utp.reservas.model.entity.Usuario;
import com.utp.reservas.repository.ReservaRepository;
import com.utp.reservas.repository.SpaceRepository;
import com.utp.reservas.repository.UsuarioRepository;
import com.utp.reservas.service.ReservaService;
import com.utp.reservas.util.CodigoReservaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private CodigoReservaGenerator codigoReservaGenerator;

    @Override
    public Reserva crearReserva(Reserva reserva, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Space espacio = spaceRepository.findById(reserva.getEspacio().getId())
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));

        if (reservaRepository.findReservasConflictivas(
                espacio.getId(), reserva.getFechaHoraInicio(), reserva.getFechaHoraFin()).isEmpty()) {

            String codigoReserva = codigoReservaGenerator.generarCodigo(reserva.getFechaHoraInicio());
            reserva.setCodigoReserva(codigoReserva);

            reserva.setUsuario(usuario);
            reserva.setEspacio(espacio);
            reserva.setEstado(EstadoReserva.PENDIENTE);

            return reservaRepository.save(reserva);
        } else {
            throw new IllegalStateException("El espacio no está disponible en el horario seleccionado.");
        }
    }


    @Override
    public List<ReservaDTO> listarTodasReservas() {
        return reservaRepository.findAll().stream()
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> listarReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuario_Id(usuarioId).stream()
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Reserva confirmarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden confirmar reservas pendientes.");
        }

        reserva.setEstado(EstadoReserva.CONFIRMADA);
        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva cancelarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("La reserva ya está cancelada.");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Space> listarEspaciosDisponibles(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        return spaceRepository.findAll().stream()
                .filter(space -> reservaRepository.findReservasConflictivas(
                        space.getId(), fechaHoraInicio, fechaHoraFin).isEmpty())
                .collect(Collectors.toList());
    }
}

