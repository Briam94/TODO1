package com.example.services;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.example.dao.TipoProductoDao;
import com.example.dto.ListaTipoProductoDto;
import com.example.dto.RespuestaGeneralDto;
import com.example.entities.TipoProducto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipoProductoService {

	/*
	 * Instancias Log para el uso de logs
	 */
	private static final Logger logger = LogManager.getLogger(TipoProductoService.class);
	private static final String HILO = "Hilo";
	
	/*
	 * Instancia de TipoProductoDao para el uso de sus metodos
	 */
	@Autowired
	private TipoProductoDao tipoProductoDao;

	/*
	 * Metodo para agregar un nuevo tipo producto
	 * recibe un dato tipo String con estructura JSON 
	 */
	public String agregar(String trama) throws JsonProcessingException {
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", Trama entrante para agregar un nuevo tipo de producto: " + trama);
		String resultado = "";
		TipoProducto tipoProducto = new TipoProducto();
		RespuestaGeneralDto respuestaGeneral = new RespuestaGeneralDto();
		try {
			/*
			 * Se convierte el dato String a estructura json para ser manipulado en JAVA
			 */
			JSONObject obj = new JSONObject(trama);
			String tipoProductoNombre = obj.getString("tipoProducto");
			tipoProducto = tipoProductoDao.consultarPorNombre(tipoProductoNombre);
			/*
			 * Validacion si ya existe un tipo producto con el mismo nombre en la base de datos
			 */
			if(tipoProducto.getTipoProducto() == null) {
				/*
				 * En caso de que no exista
				 * Se realiza la creacion del mismo
				 */
				tipoProducto.setTipoProducto(obj.getString("tipoProducto"));
				tipoProducto.setTipoProductoFechaCreacion(new Date());
				tipoProductoDao.update(tipoProducto);
				respuestaGeneral.setTipoRespuesta("0");
				respuestaGeneral.setRespuesta("Tipo de producto guardado exitosamente.");
			}else {
				/*
				 * Si ya existe el tipo producto
				 * Se devuelve un mensaje indicandole esto al cliente
				 */
				respuestaGeneral.setTipoRespuesta("1");
				respuestaGeneral.setRespuesta("Ya existe un tipo de producto con el nombre ingresado.");
			}
		} catch (Exception e) {
			/*
			 * En caso de un error, este se mostrara en los logs
			 * Sera enviada la respuesta correspondiente al cliente.
			 */
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "TipoProductoService.agregar, "
					+ ", descripcion: "
					+ e.getMessage());
			respuestaGeneral.setTipoRespuesta("1");
			respuestaGeneral.setRespuesta("Error al agregar el nuevo tipo de producto, por favor valide e intente nuevamente.");
			/*
			 * Se mapea la respuesta con una estructura json en un dato tipo String
			 */
			resultado = new ObjectMapper().writeValueAsString(respuestaGeneral);
			return resultado;
		}
		/*
		 * Se mapea la respuesta con una estructura json en un dato tipo String
		 */
		resultado = new ObjectMapper().writeValueAsString(respuestaGeneral);
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", Respuesta de agregar nuevo tipo de proudcto: " + resultado );
		return resultado;
	}

	/*
	 * Metodo para listar todos los tipos de productos registrados en la base de datos
	 */
	public String listaTipoProducto() {
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", Consultar lista de tipos de productos en el servicio");
		String respuesta = "";
		RespuestaGeneralDto respuestaGeneral = new RespuestaGeneralDto();
		try {
			/*
			 * Se realiza la busqueda de los tipos de productos en la base de datos usando el metodo findAll del tipoProductoDao
			 */
			List<TipoProducto> listaTipoProducto = tipoProductoDao.findAll();
			/*
			 * Validacion de que listaTipoProducto no este vacia 
			 */
			if(!listaTipoProducto.isEmpty()) {
				/*
				 * Si listaTipoProducto no esta vacia, se almacena en la respuesta de salida
				 */
				ListaTipoProductoDto listaTipoProductoDto = new ListaTipoProductoDto();
				listaTipoProductoDto.setTipoRespuesta("0");
				listaTipoProductoDto.setLista(listaTipoProducto);
				/*
				 * Se mapea la respuesta con una estructura json en un dato tipo String
				 */
				respuesta = new ObjectMapper().writeValueAsString(listaTipoProductoDto);
			}else {
				/*
				 * Si listaTipoProducto esta vacia
				 * Sera enviada la respuesta correspondiente al cliente.
				 */
				respuestaGeneral.setTipoRespuesta("1");
				respuestaGeneral.setRespuesta("Lista vacia");
				/*
				 * Se mapea la respuesta con una estructura json en un dato tipo String
				 */
				respuesta = new ObjectMapper().writeValueAsString(respuestaGeneral);
				return respuesta;
			}
		} catch (Exception e) {
			/*
			 * En caso de un error, este se mostrara en los logs
			 * Sera enviada la respuesta correspondiente al cliente.
			 */
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "TipoProductoService.listaTipoProducto, "
					+ "error al intentar consultar todos los registro de TipoProducto en el servicio"
					+ ", descripcion: "
					+ e.getMessage());
		}
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", respuesta de consultar lista de tipos de productos en el servicio: " + respuesta);
		return respuesta;
	}

}
