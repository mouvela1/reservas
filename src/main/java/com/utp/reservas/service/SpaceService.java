package com.utp.reservas.service;

import com.utp.reservas.model.builder.SpaceBuilder;
import com.utp.reservas.model.entity.Space;
import com.utp.reservas.repository.ReservaRepository;
import com.utp.reservas.repository.SpaceRepository;
import com.utp.reservas.service.strategy.DisponibleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private DisponibleStrategy disponibleStrategy;

    public List<Space> listarEspacios() {
        return spaceRepository.findAll();
    }

    public Optional<Space> obtenerEspacioPorId(Long id) {
        return spaceRepository.findById(id);
    }

    public Space crearEspacio(Space space) {
        return spaceRepository.save(space);
    }

    public Space actualizarEspacio(Long id, Space updatedSpace) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El espacio con ID " + id + " no fue encontrado."));
        space.setNombre(updatedSpace.getNombre());
        space.setCapacidad(updatedSpace.getCapacidad());
        space.setTipoEspacio(updatedSpace.getTipoEspacio());
        space.setDisponible(updatedSpace.isDisponible());
        return spaceRepository.save(space);
    }

    /**
     * Eliminar un espacio por su ID.
     */
    public void eliminarEspacio(Long id) {
        if (!spaceRepository.existsById(id)) {
            throw new RuntimeException("El espacio con ID " + id + " no existe.");
        }
        spaceRepository.deleteById(id);
    }

    /**
     * Verificar la disponibilidad de un espacio usando el patrón Strategy.
     */
    public boolean verificarDisponibilidad(Space space) {
        return disponibleStrategy.verificarDisponibilidad(space);
    }

    /**
     * Establecer una estrategia de disponibilidad.
     */
    public void establecerEstrategiaDisponibilidad(DisponibleStrategy strategy) {
        this.disponibleStrategy = strategy;
    }

    /**
     * Listar espacios disponibles en un rango de fechas.
     */
    public List<Space> listarEspaciosDisponibles(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        List<Space> todosLosEspacios = spaceRepository.findAll();

        return todosLosEspacios.stream()
                .filter(espacio -> reservaRepository.findReservasConflictivas(
                        espacio.getId(), fechaHoraInicio, fechaHoraFin).isEmpty())
                .collect(Collectors.toList());
    }
}
