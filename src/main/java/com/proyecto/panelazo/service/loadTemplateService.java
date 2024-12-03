package com.proyecto.panelazo.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Service
public class loadTemplateService implements  IloadTemplate{
    public String loadEmailTemplate(String verificationCode) {
        try {
            // Carga el archivo HTML desde resources/templates
            ClassPathResource resource = new ClassPathResource("templates/emailTemplate.html");
            String htmlTemplate = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

            // Reemplaza el marcador {{code}} con el código real
            return htmlTemplate.replace("{{code}}", verificationCode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar la plantilla de correo electrónico");
        }
    }
}
