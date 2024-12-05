package com.utp.reservas.repository;

import com.utp.reservas.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
