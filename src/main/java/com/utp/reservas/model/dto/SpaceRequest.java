package com.utp.reservas.model.dto;

public class SpaceRequest {
    private String nombre;
    private int capacidad;
    private Long tipoEspacioId;
    private boolean disponible;

    public SpaceRequest() {}

    public SpaceRequest(String nombre, int capacidad, Long tipoEspacioId, boolean disponible) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tipoEspacioId = tipoEspacioId;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Long getTipoEspacioId() {
        return tipoEspacioId;
    }

    public void setTipoEspacioId(Long tipoEspacioId) {
        this.tipoEspacioId = tipoEspacioId;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
