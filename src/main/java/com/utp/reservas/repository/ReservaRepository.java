package com.utp.reservas.repository;

import com.utp.reservas.model.entity.Reserva;
import com.utp.reservas.model.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByEspacio_IdAndFechaHoraInicioBetween(Long espacioId, LocalDateTime inicio, LocalDateTime fin);
    List<Reserva> findByUsuario_Id(Long usuarioId);
    List<Reserva> findByEstadoAndFechaHoraFinBefore(EstadoReserva estado, LocalDateTime fechaHoraFin);

    @Query("SELECT r FROM Reserva r WHERE r.espacio.id = :espacioId AND " +
            "(r.fechaHoraInicio < :fechaHoraFin AND r.fechaHoraFin > :fechaHoraInicio)")
    List<Reserva> findReservasConflictivas(Long espacioId, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin);

    @Query("SELECT r FROM Reserva r WHERE r.espacio.id = :espacioId AND " +
            "(r.fechaHoraInicio < :fechaHoraFin AND r.fechaHoraFin > :fechaHoraInicio) AND " +
            "r.estado NOT IN (:estadosIgnorados)")
    List<Reserva> ValidarDispo(
            Long espacioId,
            LocalDateTime fechaHoraInicio,
            LocalDateTime fechaHoraFin,
            List<EstadoReserva> estadosIgnorados);

    boolean existsByCodigoReserva(String codigoReserva);
}
