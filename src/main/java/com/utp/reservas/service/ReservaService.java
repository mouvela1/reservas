package com.utp.reservas.service;

import com.utp.reservas.dto.ReservaDTO;
import com.utp.reservas.mapper.ReservaMapper;
import com.utp.reservas.model.entity.EstadoReserva;
import com.utp.reservas.model.entity.Reserva;
import com.utp.reservas.model.entity.Usuario;
import com.utp.reservas.model.entity.Space;
import com.utp.reservas.exception.ExceptionsCustums;
import com.utp.reservas.repository.SpaceRepository;
import com.utp.reservas.repository.ReservaRepository;
import com.utp.reservas.repository.UsuarioRepository;
import com.utp.reservas.util.CodigoReservaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;


@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    public Reserva crearReserva(Reserva reserva, String username) {

        // Validar el usuario autenticado
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        System.out.println("Espacio enviado: " + reserva.getEspacio());
        System.out.println("Espacio ID enviado: " + reserva.getEspacio().getId());
        System.out.println("Usuario autenticado: " + usuario.getUsername());

        // Validar el espacio
        if (reserva.getEspacio() == null || reserva.getEspacio().getId() == null) {
            throw new EspacioNoValidoException("El espacio es nulo o no tiene un ID válido.");
        }
        Space espacio = spaceRepository.findById(reserva.getEspacio().getId())
                .orElseThrow(() -> new EspacioNoEncontradoException("Espacio no encontrado"));

        // Validar fechas
        if (reserva.getFechaHoraInicio() == null || reserva.getFechaHoraFin() == null) {
            throw new FechasInvalidasException("Las fechas de inicio y fin son obligatorias.");
        }
        if (!reserva.getFechaHoraInicio().isBefore(reserva.getFechaHoraFin())) {
            throw new FechasInvalidasException("La fecha de inicio debe ser anterior a la fecha de fin.");
        }

        // Verificar disponibilidad
        List<Reserva> reservasConflicto = reservaRepository.findByEspacio_IdAndFechaHoraInicioBetween(
                espacio.getId(),
                reserva.getFechaHoraInicio(),
                reserva.getFechaHoraFin()
        );
        if (!reservasConflicto.isEmpty()) {
            System.out.println("Reservas conflictivas detectadas: " + reservasConflicto.size());
            throw new EspacioNoDisponibleException("El espacio no está disponible en el horario solicitado.");
        }

        // Establecer valores en la reserva
        reserva.setUsuario(usuario);
        reserva.setDniUsuario(usuario.getDni());
        reserva.setEspacio(espacio); // Asignar el espacio validado
        reserva.setCodigoReserva(CodigoReservaGenerator.generarCodigo(reserva.getFechaHoraInicio()));
        reserva.setEstado(EstadoReserva.PENDIENTE);



        // Guardar reserva
        return reservaRepository.save(reserva);
    }


    public List<ReservaDTO> listarTodasReservas() {
        return reservaRepository.findAll().stream()
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> listarReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuario_Id(usuarioId).stream()
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    public List<Space> listarEspaciosDisponibles(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        // Obtener todos los espacios
        List<Space> todosLosEspacios = spaceRepository.findAll();

        // Filtrar los espacios que no tienen reservas conflictivas
        return todosLosEspacios.stream()
                .filter(espacio -> reservaRepository.findReservasConflictivas(
                        espacio.getId(), fechaHoraInicio, fechaHoraFin).isEmpty())
                .collect(Collectors.toList());
    }

    public class UsuarioNoEncontradoException extends RuntimeException {
        public UsuarioNoEncontradoException(String message) {
            super(message);
        }
    }

    public class EspacioNoValidoException extends RuntimeException {
        public EspacioNoValidoException(String message) {
            super(message);
        }
    }

    public class EspacioNoEncontradoException extends RuntimeException {
        public EspacioNoEncontradoException(String message) {
            super(message);
        }
    }

    public class EspacioNoDisponibleException extends RuntimeException {
        public EspacioNoDisponibleException(String message) {
            super(message);
        }
    }

    public class FechasInvalidasException extends RuntimeException {
        public FechasInvalidasException(String message) {
            super(message);
        }
    }

}
