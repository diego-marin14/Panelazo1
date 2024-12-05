package com.proyecto.panelazo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.panelazo.model.Orden;
import com.proyecto.panelazo.model.Usuario;
import com.proyecto.panelazo.service.IOrdenService;
import com.proyecto.panelazo.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger logger=LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	// /usuario/registro
	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario,HttpSession session) {
		logger.info("Usuario registro: {}",usuario);
		usuario.setTipo("USER");
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		Usuario user= usuarioService.save(usuario);
		session.setAttribute("idusuario", user.getId());
		return "redirect:/verify";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "usuario/login";
	}

	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		logger.info("Intento de acceso: {}", usuario.getUsername());

		// Buscar el usuario por nombre de usuario o correo electrónico
		Optional<Usuario> user = usuarioService.findByEmail(usuario.getUsername());



		if (user.isPresent() && passwordEncoder.matches(usuario.getPassword(), user.get().getPassword())) {
			// Configurar atributos de sesión
			session.setAttribute("idusuario", user.get().getId());
			session.setAttribute("username", user.get().getUsername());
			session.setAttribute("role", user.get().getTipo());

			logger.info("Acceso exitoso para el usuario: {}", user.get().getUsername());
			if(!user.get().isVerificado()){
				return "redirect:/verify";
			}
			// Redirigir según el rol del usuario
			if (user.get().getTipo().equalsIgnoreCase("ADMIN")) {
				return "redirect:/administrator";
			} else {
				return "redirect:/";
			}
		} else {
			logger.warn("Usuario no encontrado o contraseña incorrecta.");
			session.setAttribute("loginError", "Usuario o contraseña incorrectos.");
		}

		return "redirect:/usuario/login";  // Redirige de vuelta a la página de login
	}
	
	@GetMapping("/compras")
	public String obtenerCompras(Model model, HttpSession session) {
		model.addAttribute("sesion",session.getAttribute("idusuario"));
		Usuario usuario= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Orden> ordenes = ordenService.findByUsuario(usuario);
		
		model.addAttribute("ordenes",ordenes);
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id,HttpSession session, Model model) {
		logger.info("Id de la orden: {}",id);
		Optional<Orden> orden=ordenService.findById(id);
		
		model.addAttribute("detalles",orden.get().getDetalle());		
		//sesion
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuario/detallecompra";
		
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		
		return "redirect:/";
	}
	

}
