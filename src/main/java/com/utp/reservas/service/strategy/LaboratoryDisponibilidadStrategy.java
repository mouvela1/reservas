package com.utp.reservas.service.strategy;

import com.utp.reservas.model.entity.Space;

public class LaboratoryDisponibilidadStrategy implements DisponibleStrategy {

    @Override
    public boolean verificarDisponibilidad(Space space) {return  space.isDisponible();};
}
