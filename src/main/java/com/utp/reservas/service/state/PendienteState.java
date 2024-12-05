package com.utp.reservas.service.state;

import com.utp.reservas.model.entity.EstadoReserva;
import com.utp.reservas.model.entity.Reserva;

public class PendienteState implements ReservaState {

    @Override
    public void manejarEstado(Reserva reserva) {
        // Lógica para manejar reservas pendientes
        reserva.setEstado(EstadoReserva.PENDIENTE);
        System.out.println("Reserva en estado: PENDIENTE");
    }
}
