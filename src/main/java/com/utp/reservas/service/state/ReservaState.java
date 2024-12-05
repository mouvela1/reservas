package com.utp.reservas.service.state;

import com.utp.reservas.model.entity.Reserva;

public interface ReservaState {
    void manejarEstado(Reserva reserva);
}
