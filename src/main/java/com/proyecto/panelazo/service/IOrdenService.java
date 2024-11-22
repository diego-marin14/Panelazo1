package com.proyecto.panelazo.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.panelazo.model.Orden;
import com.proyecto.panelazo.model.Usuario;

public interface IOrdenService {
	
	List<Orden> findAll();
	Optional<Orden> findById(Integer id);
	Orden save(Orden orden);
	String generarNumeroOrden();
	List<Orden> findByUsuario (Usuario usuario);

}
