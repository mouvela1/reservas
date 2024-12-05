package com.utp.reservas.repository;

import com.utp.reservas.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByEspacioIdAndFechaHoraInicioBetween(Long espacioId, LocalDateTime inicio, LocalDateTime fin);
}
