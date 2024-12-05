package com.utp.reservas.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CodigoReservaGenerator {

    public static String generarCodigo(LocalDateTime fechaHoraInicio) {
        String fecha = fechaHoraInicio.format(DateTimeFormatter.ofPattern("ddMMyy"));
        String alfanumerico = generarAlfanumericoUnico(7);
        return "RES-" + fecha + alfanumerico;
    }

    private static String generarAlfanumericoUnico(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(caracteres.length());
            builder.append(caracteres.charAt(index));
        }

        return builder.toString();
    }
}


