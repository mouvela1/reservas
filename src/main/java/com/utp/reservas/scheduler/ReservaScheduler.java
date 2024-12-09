package com.utp.reservas.scheduler;

import com.utp.reservas.model.entity.Reserva;
import com.utp.reservas.model.enums.EstadoReserva;
import com.utp.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservaScheduler {

    @Autowired
    private ReservaRepository reservaRepository;

    // Configuración para que se ejecute cada hora (puedes ajustar el cron según tu necesidad)
    @Scheduled(cron = "0 0 * * * ?") // Cada hora
    public void actualizarReservasAUsadas() {
        LocalDateTime ahora = LocalDateTime.now();

        // Buscar reservas confirmadas cuya fecha y hora de fin ya haya pasado
        List<Reserva> reservasParaActualizar = reservaRepository.findByEstadoAndFechaHoraFinBefore(
                EstadoReserva.CONFIRMADA, ahora);

        for (Reserva reserva : reservasParaActualizar) {
            reserva.setEstado(EstadoReserva.USADA);
            reservaRepository.save(reserva);
        }
    }
}
