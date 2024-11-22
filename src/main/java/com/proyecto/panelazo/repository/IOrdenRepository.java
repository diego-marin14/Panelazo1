package com.proyecto.panelazo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.panelazo.model.Orden;
import com.proyecto.panelazo.model.Usuario;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden,Integer> {
	List<Orden> findByUsuario (Usuario usuario);


}
