package com.utp.reservas.model.dto;

public class LoginResponse {
    private String username;
    private String token;
    private String nombreCompleto;

    // Constructor
    public LoginResponse(String username, String token, String nombreCompleto) {
        this.username = username;
        this.token = token;
        this.nombreCompleto = nombreCompleto;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
