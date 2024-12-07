package com.utp.reservas.service.state;

import com.utp.reservas.model.enums.EstadoReserva;
import com.utp.reservas.model.entity.Reserva;

public class CanceladaState implements ReservaState {

    @Override
    public void manejarEstado(Reserva reserva) {
        // Lógica para manejar reservas canceladas
        reserva.setEstado(EstadoReserva.CANCELADA);
        System.out.println("Reserva en estado: CANCELADA");
    }
}
