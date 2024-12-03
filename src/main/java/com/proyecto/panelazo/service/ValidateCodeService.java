package com.proyecto.panelazo.service;

import com.proyecto.panelazo.model.Verification;
import com.proyecto.panelazo.repository.IverificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class ValidateCodeService implements IvalidateCode{
    @Autowired
    private IverificationRepository verificationRepository;

    public String validateCode(String code, Integer usuarioId) {
        Optional<Verification> verificationOptional = verificationRepository.findByCodeAndUsuarioId(code, usuarioId);

        if (!verificationOptional.isPresent()) {
            throw new RuntimeException("El código no existe o no pertenece al usuario.");
        }

        Verification verification = verificationOptional.get();

        if (verification.isUsed()) {
            throw new  RuntimeException("El código ya ha sido usado.");
        }

        if (verification.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException( "El código ha expirado.");
        }

        // Marca el código como usado
        verification.setUsed(true);
        verificationRepository.save(verification);

        return "Código verificado con éxito.";
    }
}
