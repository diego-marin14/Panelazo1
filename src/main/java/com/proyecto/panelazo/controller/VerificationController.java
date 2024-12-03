package com.proyecto.panelazo.controller;

import com.proyecto.panelazo.model.Usuario;
import com.proyecto.panelazo.service.IUsuarioService;
import com.proyecto.panelazo.service.IgenerateCodeVerify;
import com.proyecto.panelazo.service.IvalidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class VerificationController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IgenerateCodeVerify generateCodeVerify;

    @Autowired
    private IvalidateCode validateCodeService;

    @Autowired
    private IUsuarioService usarioService;




    @GetMapping("/verify")
    public String showVerificationPage(Model model, HttpSession session) {
        Usuario usuario= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();


        generateCodeVerify.generateCode(usuario.getEmail());
        model.addAttribute("verificationCode", new String[6]);  // Inicializa el código como un array vacío de longitud 6
        return "usuario/verification";
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam("code") String code, HttpSession session,Model model) {


        try {
            String codeSincomas = code.replace(",", "");

            // Paso 2: Elimina los espacios extras entre palabras
            String codelimpio = codeSincomas.replaceAll("\\s+", " ").trim();
            validateCodeService.validateCode(codelimpio, Integer.parseInt(session.getAttribute("idusuario").toString()));
            usarioService.verificarUser(Integer.parseInt(session.getAttribute("idusuario").toString()));
            String rol= session.getAttribute("idusuario").toString();
            if (rol.equalsIgnoreCase("ADMIN")) {
                return "redirect:/administrador";
            } else {
                return "redirect:/";
            }
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "usuario/verification";
        }


    }
}
