package com.example.services;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ProductoDao;
import com.example.dao.VentaConsolidadoDao;
import com.example.dao.VentaDao;
import com.example.dto.ListaVentaDto;
import com.example.dto.RespuestaGeneralDto;
import com.example.entities.Producto;
import com.example.entities.Venta;
import com.example.entities.VentaConsolidado;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VentaService {

	/*
	 * Instancias Log para el uso de logs
	 */
	private static final Logger logger = LogManager.getLogger(VentaService.class);
	private static final String HILO = "Hilo";

	/*
	 * Instancia de VentaDao para el uso de sus metodos
	 */
	@Autowired
	private VentaDao ventaDao;
	
	/*
	 * Instancia de ProductoDao para el uso de sus metodos
	 */
	@Autowired
	private ProductoDao productoDao;
	/*
	 * Instancia de VentaConsolidadoDao para el uso de sus metodos
	 */
	@Autowired
	private VentaConsolidadoDao ventaConsolidadoDao;

	/*
	 * Metodo para listar todas las ventas registradas en la base de datos
	 */
	public String listarVenta() {
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", VentaService.listrVenta listar todas las ventas");
		String resultado = "";
		RespuestaGeneralDto respuestaGeneral = new RespuestaGeneralDto();
		try {
			ListaVentaDto listaVenta = new ListaVentaDto();
			/*
			 * Se realiza la busqueda de las ventas registradas en la base de datos findAll del ventaDao
			 */
			List<Venta> ventas = ventaDao.findAll();
			/*
			 * Validacion de que ventas no este vacia 
			 */
			if(!ventas.isEmpty()) {
				/*
				 * Si ventas no esta vacia, se almacena en la respuesta de salida
				 */
				listaVenta.setTipoRespuesta("0");
				listaVenta.setListaVenta(ventas);
				/*
				 * Se mapea la respuesta con una estructura json en un dato tipo String
				 */
				resultado = new ObjectMapper().writeValueAsString(listaVenta);
			}
		} catch (Exception e) {
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "VentaService.listrVenta, "
					+ ", descripcion: "
					+ e.getMessage());
			respuestaGeneral.setTipoRespuesta("1");
			respuestaGeneral.setRespuesta("Error al listar la venta, porfavor intente mas tarde.");
			try {
				/*
				 * Se mapea la respuesta con una estructura json en un dato tipo String
				 */
				resultado = new ObjectMapper().writeValueAsString(respuestaGeneral);
			} catch (JsonProcessingException e1) {
			}
		}
		return resultado;
	}

	/*
	 * Metodo para agregar una nueva venta
	 * recibe un dato tipo String con estructura JSON 
	 */
	public String registrarVenta(String trama) throws JsonProcessingException {
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", VentaService.registrarVenta trama entrante para registrar venta: " + trama);
		String resultado = "";
		RespuestaGeneralDto respuestaGeneral = new RespuestaGeneralDto();
		try {
			/*
			 * Se convierte el dato String a estructura json para ser manipulado en JAVA
			 */
			JSONObject obj = new JSONObject(trama);
			String documento = obj.getString("documento");
			Long totalPagar = obj.getLong("totalPagar");
			Date fechaVenta = new Date();
			Venta venta = new Venta();
			venta.setVentaTotalPagar(totalPagar);
			venta.setVentaDocumento(documento);
			venta.setVentaFecha(fechaVenta);
			venta.setVentaCantidad(obj.getLong("cantidadTotal"));
			/*
			 * Se realiza la creacion del registro en la venta
			 */
			ventaDao.update(venta);
			/*
			 * Se realiza la busqueda de la venta anteriormente registrada ventaBusqueda
			 */
			Venta ventaBusqueda = ventaDao.findByAll(documento, fechaVenta, totalPagar);
			/*
			 * Se obtienen las listas de los productos asociados a la venta
			 * Se almacenan como un array de tipo JSONArray : ventaArr
			 */
			JSONArray ventaArr = obj.getJSONArray("ventas");
			for (int i = 0; i < ventaArr.length(); i++) {
				VentaConsolidado ventaConsolidado = new VentaConsolidado();
				ventaConsolidado.setVentaConsolidadoVentaId(ventaBusqueda);
				Producto producto = productoDao.buscarPorId(ventaArr.getJSONObject(i).getLong("productoId"));
				ventaConsolidado.setVentaConsolidadoProductoId(producto);
				ventaConsolidado.setVentaConsolidadoCantidad(ventaArr.getJSONObject(i).getLong("productoCantidad"));
				/*
				 * Se crea el registro de venta consolidado en la base de datos por cada producto en la lista ventaArr
				 */
				ventaConsolidadoDao.update(ventaConsolidado);
			}
			/*
			 * Se registra el tipo de respuesta
			 */
			respuestaGeneral.setTipoRespuesta("0");
			respuestaGeneral.setRespuesta("Venta registrada exitosamente.");
		} catch (Exception e) {
			/*
			 * En caso de un error, este se mostrara en los logs
			 * Sera enviada la respuesta correspondiente al cliente.
			 */
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "VentaService.registrarVenta, "
					+ ", descripcion: "
					+ e.getMessage());
			respuestaGeneral.setTipoRespuesta("1");
			respuestaGeneral.setRespuesta("Error al registrar venta, por favor intente mas tarde.");
		}
		/*
		 * Se mapea la respuesta con una estructura json en un dato tipo String
		 */
		resultado = new ObjectMapper().writeValueAsString(respuestaGeneral);
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", VentaService.registrarVenta resultado: " + resultado);
		return resultado;
	}

}
