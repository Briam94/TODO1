package com.example.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

import com.example.entities.VentaConsolidado;

@Repository
@EntityScan("com.example.entities")
public class VentaConsolidadoDao extends AbstractDao<VentaConsolidado> {

	/*
	 * Instancia  de EntityManager para el uso de JPA
	 */
	@PersistenceContext
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public VentaConsolidadoDao() {
		super(VentaConsolidado.class);
	}
	
}
