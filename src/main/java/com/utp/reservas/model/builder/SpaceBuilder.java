package com.utp.reservas.model.builder;

import com.utp.reservas.model.entity.Space;
import com.utp.reservas.model.entity.SpaceType;

public class SpaceBuilder {

    private String nombre;
    private int capacidad;
    private SpaceType tipoEspacio;
    private boolean disponible;

    public SpaceBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public SpaceBuilder setCapacidad(int capacidad) {
        this.capacidad = capacidad;
        return this;
    }

    public SpaceBuilder setTipoEspacio(SpaceType tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
        return this;
    }

    public SpaceBuilder setDisponible(boolean disponible) {
        this.disponible = disponible;
        return this;
    }

    public Space build() {
        if (this.nombre == null || this.nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del espacio no puede ser nulo o vacío");
        }
        Space space = new Space();
        space.setNombre(this.nombre);
        space.setCapacidad(this.capacidad);
        space.setTipoEspacio(this.tipoEspacio);
        space.setDisponible(this.disponible);
        return space;
    }
}
