package com.utp.reservas.service.impl;

import com.utp.reservas.model.entity.Reserva;
import com.utp.reservas.repository.ReservationRepository;
import com.utp.reservas.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reserva crearReserva(Reserva reserva) {
        if (verificarDisponibilidad(reserva.getEspacio().getId(), reserva)) {
            return reservationRepository.save(reserva);
        } else {
            throw new RuntimeException("El espacio no está disponible en el horario seleccionado.");
        }
    }

    @Override
    public Reserva obtenerReservaPorId(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reserva> listarReservas() {
        return reservationRepository.findAll();
    }

    @Override
    public void eliminarReserva(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public boolean verificarDisponibilidad(Long espacioId, Reserva reserva) {
        List<Reserva> reservas = reservationRepository.findByEspacioIdAndFechaHoraInicioBetween(
                espacioId,
                reserva.getFechaHoraInicio(),
                reserva.getFechaHoraFin()
        );
        return reservas.isEmpty();
    }
}
