package com.proyecto.panelazo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.panelazo.model.DetalleOrden;

@Repository
public interface IDetalleOrdenRepository  extends JpaRepository<DetalleOrden, Integer> {

}
