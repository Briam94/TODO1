package com.example.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

import com.example.entities.TipoProducto;

@Repository
@EntityScan("com.example.entities")
public class TipoProductoDao extends AbstractDao<TipoProducto> {

	/*
	 * Instancias Log para el uso de logs
	 */
	private static final Logger logger = LogManager.getLogger(TipoProductoDao.class);
	private static final String HILO = "Hilo";
	
	@PersistenceContext
	private EntityManager em;

	/*
	 * Instancia  de EntityManager para el uso de JPA
	 */
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public TipoProductoDao() {
		super(TipoProducto.class);
	}

	/*
	 * Metodo para buscar TipoProducto por id registrado en la base de datos
	 */
	public TipoProducto consultarPorId(Long tipoProductoId) {
		TipoProducto tipoProducto = new TipoProducto();
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", TipoProductoDao.consultarPorId id de tipo de producto a consultar: "
  				+ tipoProductoId);
		try {
			/*
			 * Se realiza el llamado a la consulta findById en la clase TipoProducto
			 */
			Query buscar = em.createNamedQuery("TipoProducto.findById").setParameter("Id", tipoProductoId);
			tipoProducto = (TipoProducto) buscar.getSingleResult();
			em.close();
		} catch (Exception e) {
			String descripcion = ", descripcion";
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "TipoProductoDao.consultarPorId, "
					+ "No se encontro un TipoProducto con el id: " + tipoProductoId + descripcion
					+ e.getMessage());
		}
		return tipoProducto;
	}

	/*
	 * Metodo para buscar TipoProducto por nombre registrado en la base de datos
	 */
	public TipoProducto consultarPorNombre(String nombre) {
		TipoProducto tipoProducto = new TipoProducto();
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", TipoProductoDao.consultarPorNombre nombre de tipo de producto a consultar: "
  				+ nombre);
  		try {
			/*
			 * Se realiza el llamado a la consulta findByName en la clase TipoProducto
			 */
			Query buscar = em.createNamedQuery("TipoProducto.findByName").setParameter("nombre", nombre);
			tipoProducto = (TipoProducto) buscar.getSingleResult();
			em.close();
		} catch (Exception e) {
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "TipoProductoDao.consultarPorNombre, "
					+ "No se encontro un TipoProducto con el nombre de " + nombre
					+ ", descripcion: "
					+ e.getMessage());
		}
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", TipoProductoDao.consultarPorNombre se encontro un TipoProducto con el nombre de: "
  				+ nombre);
  		return tipoProducto;
	}
		
}
