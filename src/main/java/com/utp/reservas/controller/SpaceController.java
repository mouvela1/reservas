package com.utp.reservas.controller;

import com.utp.reservas.model.entity.SpaceType;
import com.utp.reservas.util.ApiResponse;
import com.utp.reservas.model.builder.SpaceBuilder;
import com.utp.reservas.model.entity.Space;
import com.utp.reservas.model.dto.SpaceRequest;
import com.utp.reservas.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'PROFESOR', 'ADMINISTRATIVO', 'ADMIN')")
    public ResponseEntity<List<Space>> listSpaces() {
        return ResponseEntity.ok(spaceService.listarEspacios());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<Space> getSpaceById(@PathVariable Long id) {
        return spaceService.obtenerEspacioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<Space> createSpace(@RequestBody SpaceRequest spaceRequest) {
        // Construye el Space usando SpaceBuilder
        Space space = new SpaceBuilder()
                .setNombre(spaceRequest.getNombre())
                .setCapacidad(spaceRequest.getCapacidad())
                .setTipoEspacio(new SpaceType(spaceRequest.getTipoEspacioId())) // Asume que SpaceType tiene un constructor que acepta un ID
                .setDisponible(spaceRequest.isDisponible())
                .build();

        return ResponseEntity.ok(spaceService.crearEspacio(space));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<Space> updateSpace(@PathVariable Long id, @RequestBody Space updatedSpace) {
        return ResponseEntity.ok(spaceService.actualizarEspacio(id, updatedSpace));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        spaceService.eliminarEspacio(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/disponibles")
    public ResponseEntity<ApiResponse<?>> listarEspaciosDisponibles(@RequestBody Map<String, String> payload) {
        // Extraer fechas del payload
        String fechaInicioStr = payload.get("fechaHoraInicio");
        String fechaFinStr = payload.get("fechaHoraFin");

        if (fechaInicioStr == null || fechaFinStr == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Los parámetros 'fechaHoraInicio' y 'fechaHoraFin' son obligatorios.", null));
        }
        LocalDateTime fechaHoraInicio;
        LocalDateTime fechaHoraFin;
        try {
            fechaHoraInicio = LocalDateTime.parse(fechaInicioStr);
            fechaHoraFin = LocalDateTime.parse(fechaFinStr);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("El formato de las fechas es inválido. Debe ser 'YYYY-MM-DDTHH:MM:SS'.", null));
        }

        if (fechaHoraInicio.isAfter(fechaHoraFin)) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("El rango de fechas es inválido. 'fechaHoraInicio' debe ser anterior a 'fechaHoraFin'.", null));
        }

        List<Space> espaciosDisponibles = spaceService.listarEspaciosDisponibles(fechaHoraInicio, fechaHoraFin);
        return ResponseEntity.ok(new ApiResponse<>("Espacios disponibles obtenidos con éxito.", espaciosDisponibles));
    }


}

