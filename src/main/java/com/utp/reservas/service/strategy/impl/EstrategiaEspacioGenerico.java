package com.utp.reservas.service.strategy.impl;

import com.utp.reservas.model.entity.Space;
import com.utp.reservas.service.strategy.DisponibleStrategy;
import org.springframework.stereotype.Component;

@Component // Marca esta clase como un bean de Spring
public class EstrategiaEspacioGenerico implements DisponibleStrategy {

    @Override
    public boolean verificarDisponibilidad(Space space) {
        // Verifica si el espacio está disponible
        return space.isDisponible();
    }
}

