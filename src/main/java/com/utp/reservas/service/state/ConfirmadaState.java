package com.utp.reservas.service.state;

import com.utp.reservas.model.entity.EstadoReserva;
import com.utp.reservas.model.entity.Reserva;

public class ConfirmadaState implements ReservaState {

    @Override
    public void manejarEstado(Reserva reserva) {
        // Lógica para manejar reservas confirmadas
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        System.out.println("Reserva en estado: CONFIRMADA");
    }
}
