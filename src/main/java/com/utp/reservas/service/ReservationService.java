package com.utp.reservas.service;

import com.utp.reservas.model.entity.Reserva;
import java.util.List;

public interface ReservationService {
    Reserva crearReserva(Reserva reserva);
    Reserva obtenerReservaPorId(Long id);
    List<Reserva> listarReservas();
    void eliminarReserva(Long id);
    boolean verificarDisponibilidad(Long espacioId, Reserva reserva);
}

