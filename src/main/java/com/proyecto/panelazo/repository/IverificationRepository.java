package com.proyecto.panelazo.repository;

import com.proyecto.panelazo.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IverificationRepository extends JpaRepository<Verification,Long> {
    Optional<Verification> findByCodeAndUsuarioId(String code, Integer usuarioId);

}
