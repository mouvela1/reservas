package com.utp.reservas.repository;

import com.utp.reservas.model.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    List<Space> findByTipoEspacio_Nombre(String nombre);
}

