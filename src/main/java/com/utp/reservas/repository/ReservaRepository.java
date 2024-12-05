package com.utp.reservas.repository;

import com.utp.reservas.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByEspacio_IdAndFechaHoraInicioBetween(Long espacioId, LocalDateTime inicio, LocalDateTime fin);
    List<Reserva> findByUsuario_Id(Long usuarioId);

    @Query("SELECT r FROM Reserva r WHERE r.espacio.id = :espacioId AND " +
            "(r.fechaHoraInicio < :fechaHoraFin AND r.fechaHoraFin > :fechaHoraInicio)")
    List<Reserva> findReservasConflictivas(Long espacioId, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin);

}
