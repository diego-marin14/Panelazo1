package com.proyecto.panelazo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.proyecto.panelazo.model.Verification;
import com.proyecto.panelazo.repository.IverificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.panelazo.model.Usuario;
import com.proyecto.panelazo.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;


	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}


	@Override
	public Usuario save(Usuario usuario) {
		var usuarioSaved=usuarioRepository.save(usuario);

		return usuarioSaved;
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public void verificarUser(Integer id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontró usuario con ID: " + id));

		usuario.setVerificado(true);
		usuarioRepository.save(usuario);

	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}
	
	


}
