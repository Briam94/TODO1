package com.example.services;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.VentaConsolidadoDao;
import com.example.dto.ListaVentaConsolidadoDto;
import com.example.dto.RespuestaGeneralDto;
import com.example.entities.VentaConsolidado;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class VentaConsolidadoService {

	/*
	 * Instancias Log para el uso de logs
	 */
	private static final Logger logger = LogManager.getLogger(VentaConsolidadoService.class);
	private static final String HILO = "Hilo";
	

	/*
	 * Instancia de VentaConsolidadoDao para el uso de sus metodos
	 */
	@Autowired
	private VentaConsolidadoDao ventaConsolidadoDao;

	/*
	 * Metodo para listar todas las ventas consolidadas registradas en la base de datos
	 */
	public String listarVentaConsolidado() throws JsonProcessingException {
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", VentaConsolidadoService.listarVentaConsolidado listar todas las ventas");
		String resultado = "";
		RespuestaGeneralDto respuestaGeneral = new RespuestaGeneralDto();
		try {
			ListaVentaConsolidadoDto listaVentaConsolidado = new ListaVentaConsolidadoDto();
			/*
			 * Se realiza la busqueda de las ventas consolidadas registradas en la base de datos findAll del ventaConsolidadoDao
			 */
			List<VentaConsolidado> ventaConsolidado = ventaConsolidadoDao.findAll();
			/*
			 * Validacion de que ventaConsolidado no este vacia 
			 */
			if(!ventaConsolidado.isEmpty()) {
				/*
				 * Si ventaConsolidado no esta vacia, se almacena en la respuesta de salida
				 */
				listaVentaConsolidado.setTipoRespuesta("0");
				listaVentaConsolidado.setVentaConsolidado(ventaConsolidado);
				/*
				 * Se mapea la respuesta con una estructura json en un dato tipo String
				 */
				resultado = new ObjectMapper().writeValueAsString(ventaConsolidado);
			}
		} catch (Exception e) {
			/*
			 * En caso de un error, este se mostrara en los logs
			 * Sera enviada la respuesta correspondiente al cliente.
			 */
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "VentaConsolidadoService.listarVentaConsolidado, "
					+ ", descripcion: "
					+ e.getMessage());
			respuestaGeneral.setTipoRespuesta("1");
			respuestaGeneral.setRespuesta("Error al registrar venta, porfavor intente mas tarde.");
			/*
			 * Se mapea la respuesta con una estructura json en un dato tipo String
			 */
			resultado = new ObjectMapper().writeValueAsString(respuestaGeneral);
		}
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", VentaConsolidadoService.listarVentaConsolidado resultado: " + resultado);
		return resultado;
	}

}
