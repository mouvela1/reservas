package com.utp.reservas.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "espacios")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre; // Nombre del espacio

    @Column(nullable = false)
    private int capacidad; // Capacidad máxima

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_espacio_id", nullable = false)
    private SpaceType tipoEspacio; // Relación con SpaceType

    @Column(nullable = false)
    private boolean disponible; // Disponibilidad

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public SpaceType getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(SpaceType tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
