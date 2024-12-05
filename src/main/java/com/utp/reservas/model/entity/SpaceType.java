package com.utp.reservas.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_espacio")
public class SpaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // Nombre del tipo de espacio

    public SpaceType() {
        // Constructor por defecto requerido por JPA
    }

    public SpaceType(Long id) {
        this.id = id;
    }

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
}

