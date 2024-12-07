package com.utp.reservas.util;

import com.utp.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class CodigoReservaGenerator {

    @Autowired
    private ReservaRepository reservaRepository;

    private static final String PREFIJO = "RES";

    public String generarCodigo(LocalDateTime fechaHora) {
        String fechaFormato = fechaHora.format(DateTimeFormatter.ofPattern("ddMMyy-HHmm"));
        String alfanumerico = generarAlfanumericoUnico();

        String codigo = PREFIJO + "-" + fechaFormato + "-" + alfanumerico;

        // Validar unicidad
        while (reservaRepository.existsByCodigoReserva(codigo)) {
            alfanumerico = generarAlfanumericoUnico();
            codigo = PREFIJO + "-" + fechaFormato + "-" + alfanumerico;
        }

        return codigo;
    }

    private String generarAlfanumericoUnico() {
        int longitud = 6;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        return sb.toString();
    }
}



