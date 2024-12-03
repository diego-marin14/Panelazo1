package com.proyecto.panelazo.service;

import com.proyecto.panelazo.model.Verification;
import com.proyecto.panelazo.repository.IUsuarioRepository;
import com.proyecto.panelazo.repository.IverificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service

public class generateCodeVerifyService  implements IgenerateCodeVerify{
    @Autowired
    private IverificationRepository verificationRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private ISendMail sendMail;

    @Override
    public void generateCode(String email) {
        var usuario=usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No se encuentra registrado"));
        var code=generateVerificationCode();
        Verification verification=Verification.builder().usuario(usuario).used(false).code(code).expirationTime(LocalDateTime.now().plusMinutes(20)).build();
        sendMail.sendEmail(usuario.getEmail(),code);
        verificationRepository.save(verification);

    }
    private  String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Rango de 6 dígitos
        return String.valueOf(code);
    }
}
