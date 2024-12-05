package com.utp.reservas.service.strategy;

import com.utp.reservas.model.entity.Space;

public interface DisponibleStrategy {
    boolean verificarDisponibilidad(Space space);
}

