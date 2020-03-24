package com.example.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

import com.example.entities.Producto;

@Repository
@EntityScan("com.example.entities")
public class ProductoDao extends AbstractDao<Producto> {

	/*
	 * Instancias Log para el uso de logs
	 */
	private static final Logger logger = LogManager.getLogger(ProductoDao.class);
	private static final String HILO = "Hilo";
	
	/*
	 * Instanciamiento de EntityManager para el uso de JPA
	 */
	@PersistenceContext
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public ProductoDao() {
		super(Producto.class);
	}
	

	/*
	 * Metodo para buscar producto por nombre registrado en la base de datos
	 */
	public Producto buscarPorNombre(String nombre){
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", ProductoDao.buscarPorNombre buscar producto producto por nombre: " + nombre);
		Producto producto = new Producto();
		try {
			/*
			 * Se realiza el llamado a la consulta findByName en la clase Producto
			 */
			Query buscar = em.createNamedQuery("Producto.findByName").setParameter("name", nombre);
			producto = (Producto) buscar.getSingleResult();
			em.close();
		} catch (Exception e) {
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "ProductoDao.buscarPorNombre, descripcion: "
					+ e.getMessage());
		}
		return producto;
	}

	/*
	 * Metodo para buscar producto por id registrado en la base de datos
	 */
	public Producto buscarPorId(Long id){
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", ProductoDao.buscarPorId buscar producto producto por id: " + id);
		Producto producto = new Producto();
		try {
			/*
			 * Se realiza el llamado a la consulta findById en la clase Producto
			 */
			Query buscar = em.createNamedQuery("Producto.findById").setParameter("id", id);
			producto = (Producto) buscar.getSingleResult();
			em.close();
		} catch (Exception e) {
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "ProductoDao.buscarPorId, "
					+ ", descripcion: "
					+ e.getMessage());
		}
		return producto;
	}


}
