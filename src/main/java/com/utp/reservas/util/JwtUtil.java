package com.utp.reservas.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Genera una clave secreta segura
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tiempo de expiración del token (1 hora en milisegundos)
    private static final long EXPIRATION_TIME = 3600000;

    // Generar un token JWT con datos adicionales (como el rol)
    public static String generarToken(String username, String rol) {
        return Jwts.builder()
                .setSubject(username) // Establece el username como subject
                .claim("rol", rol)    // Agrega el rol como un claim adicional
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiración
                .signWith(SECRET_KEY) // Firma con la clave secreta
                .compact();
    }

    // Validar y obtener el usuario desde el token
    public static String obtenerUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado");
        }
    }

    // Obtener Claims completos del token
    public static Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
