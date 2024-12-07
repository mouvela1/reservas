package com.utp.reservas.service;

import com.utp.reservas.dto.ReservaDTO;
import com.utp.reservas.model.entity.Reserva;
import com.utp.reservas.model.entity.Space;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaService {

    Reserva crearReserva(Reserva reserva, String username);

    List<ReservaDTO> listarTodasReservas();

    List<ReservaDTO> listarReservasPorUsuario(Long usuarioId);

    Reserva confirmarReserva(Long reservaId);

    Reserva cancelarReserva(Long reservaId);

    List<Space> listarEspaciosDisponibles(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin);
}


