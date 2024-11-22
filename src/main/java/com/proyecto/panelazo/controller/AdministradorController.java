package com.proyecto.panelazo.controller;

import java.util.List;

import com.proyecto.panelazo.model.Usuario;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.panelazo.model.Orden;
import com.proyecto.panelazo.model.Producto;
import com.proyecto.panelazo.service.IOrdenService;
import com.proyecto.panelazo.service.IUsuarioService;
import com.proyecto.panelazo.service.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	private Logger logg= LoggerFactory.getLogger(AdministradorController.class);
	
	@GetMapping("")
	public String home(Model model) {
		
		List<Producto> productos= productoService.findAll();
		List<Usuario>usuarios=usuarioService.findAll();
		List<Orden>ordenesPendientes=ordenService.findAll();
		model.addAttribute("productos",productos);
		model.addAttribute("produtosCount",productos.size());
		model.addAttribute("usuariosCount",usuarios.size());
		model.addAttribute("ordenesCount",ordenesPendientes.size());
		
		return "administrador/home";
	}
	
	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return "administrador/usuarios";
	}
	
	@GetMapping("/ordenes")
	public String ordenes(Model model) {
		model.addAttribute("ordenes",ordenService.findAll());
		return "administrador/ordenes";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(Model model, @PathVariable Integer id) {
		logg.info("Id de la orden: {}",id);
		Orden orden=ordenService.findById(id).get();
		
		model.addAttribute("detalles",orden.getDetalle());
		
		return "administrador/detalleorden";
		
	}

}
