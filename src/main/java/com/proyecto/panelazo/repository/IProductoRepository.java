package com.proyecto.panelazo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.panelazo.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Integer> {
	
	

}
