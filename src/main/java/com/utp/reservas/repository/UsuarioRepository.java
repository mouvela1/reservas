package com.utp.reservas.repository;

import com.utp.reservas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByDni(String dni);

    Optional<Usuario> findByCodigoEstudiante(String codigoEstudiante);

    Optional<Object> findByCorreo(String correo);


    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithRol(@Param("username") String username);
}
