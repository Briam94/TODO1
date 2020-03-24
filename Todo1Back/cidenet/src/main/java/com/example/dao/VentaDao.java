package com.example.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

import com.example.entities.Venta;

@Repository
@EntityScan("com.example.entities")
public class VentaDao extends AbstractDao<Venta>{

	/*
	 * Instancias Log para el uso de logs
	 */
	private static final Logger logger = LogManager.getLogger(VentaDao.class);
	private static final String HILO = "Hilo";

	/*
	 * Instancia  de EntityManager para el uso de JPA
	 */
	@PersistenceContext
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public VentaDao() {
		super(Venta.class);
	}

	/*
	 * Metodo para buscar la venta por documento fechaVenta, total, registrado en la base de datos
	 */
	public Venta findByAll(String documento, Date fechaVenta, Long total){
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", VentaDao.findByAll buscar venta por, documento: " + documento + ", total: " + total);
  		Venta venta = new Venta();
		try {
			/*
			 * Se realiza el llamado a la consulta findByAll en la clase Venta
			 */
			Query buscar = em.createNamedQuery("Venta.findByAll")
					.setParameter("documento", documento).setParameter("fechaVenta", fechaVenta).setParameter("total", total);
			venta = (Venta) buscar.getSingleResult();
			em.close();
		} catch (Exception e) {
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "VentaDao.findByAll, "
					+ ", descripcion: "
					+ e.getMessage());
		}
		return venta;
	}

}
