package com.proyecto.panelazo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class sendEmalService implements ISendMail{
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;
    @Autowired
    private IloadTemplate loadTemplate;
    @Override
    public void  sendEmail(String email,String code) {
        String htmlContent =loadTemplate.loadEmailTemplate(code);
        try {
            String subjet="'Codigo de verificacion'";
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // El true permite enviar contenido HTML
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject(subjet);
            helper.setText(htmlContent,true);
            javaMailSender.send(mimeMessage);
        }

        // Catch block to handle the exceptions
        catch (Exception e) {

        }
    }
}
