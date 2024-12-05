package com.utp.reservas.mapper;

import com.utp.reservas.dto.ReservaDTO;
import com.utp.reservas.model.entity.Reserva;

public class ReservaMapper {

    public static ReservaDTO toDTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setCodigoReserva(reserva.getCodigoReserva());
        dto.setNombreUsuario(reserva.getUsuario().getNombre());
        dto.setApellidoUsuario(reserva.getUsuario().getApellido());
        dto.setCodigoEstudiante(reserva.getUsuario().getCodigoEstudiante());
        dto.setDniUsuario(reserva.getUsuario().getDni());
        dto.setNombreEspacio(reserva.getEspacio().getNombre());
        dto.setFechaHoraInicio(reserva.getFechaHoraInicio());
        dto.setFechaHoraFin(reserva.getFechaHoraFin());
        dto.setEstado(reserva.getEstado().name());
        return dto;
    }
}
