package com.utp.reservas.util;

public class UsuarioUtil {

    public static String generarCodigoEstudiante(String dni, String nombre, String apellido) {
        if (dni == null || dni.length() < 3 || nombre == null || apellido == null) {
            throw new IllegalArgumentException("Datos insuficientes para generar el código de estudiante");
        }

        // Toma los 3 primeros números del DNI
        String dniSegmento = dni.substring(0, 3);

        // Obtiene las iniciales de los nombres
        String inicialesNombre = obtenerIniciales(nombre);

        // Obtiene las iniciales de los apellidos
        String inicialesApellido = obtenerIniciales(apellido);

        // Concatena con la letra 'U' al inicio
        return "U" + dniSegmento + inicialesNombre + inicialesApellido;
    }

    private static String obtenerIniciales(String texto) {
        // Divide el texto en palabras
        String[] palabras = texto.split(" ");
        StringBuilder iniciales = new StringBuilder();

        // Toma la primera letra de cada palabra
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                iniciales.append(palabra.toUpperCase().charAt(0));
            }
        }
        return iniciales.toString();
    }

    public static String generarCorreo(String CodigoStudiante) {
        return CodigoStudiante+"@utp.com";
    }
}

