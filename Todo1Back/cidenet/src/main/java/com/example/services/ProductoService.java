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

import com.example.dao.ProductoDao;
import com.example.dao.TipoProductoDao;
import com.example.dto.ListaProductosDto;
import com.example.dto.RespuestaGeneralDto;
import com.example.entities.Producto;
import com.example.entities.TipoProducto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductoService {

	/*
	 * Instancias Log para el uso de logs
	 */
	private static final Logger logger = LogManager.getLogger(ProductoService.class);
	private static final String HILO = "Hilo";
	
	/*
	 * Instancia de ProductoDao para el uso de sus metodos
	 */
	@Autowired
	private ProductoDao productoDao;
	/*
	 * Instancia de TipoProductoDao para el uso de sus metodos
	 */
	@Autowired
	private TipoProductoDao tipoProductoDao;
	
	/*
	 * Metodo para agregar o actualizar un producto, 
	 * recibe un dato tipo String con estructura JSON
	 */
	public String agregar(String trama) throws JsonProcessingException {
		String respuesta = "";
		RespuestaGeneralDto respuestaGeneral = new RespuestaGeneralDto();
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", ProductoService.agregar trama entrante para agregar o actualizar producto: " + trama);
		try {
			/*
			 * Se convierte el dato String a estructura json para ser manipulado en JAVA
			 */
			JSONObject obj = new JSONObject(trama);
			Producto producto = new Producto();
			/*
			 * use: es una bandera para identificar el tipo de solicitud a realizar:
			 * use: 0 -> agregar un nuevo proudcto a la base de datos;
			 * use: 1 -> actualizar un producto existente en la base de datos
			 */
			String use = obj.getString("use");
			if(use.equalsIgnoreCase("0")) {
				String nombre = obj.getString("nombre");
				/*
				 * Se realiza una consulta por nombre a la base de datos a la tabla producto
				 * para verificar que no existe un producto con el mismo nombre ingresado.
				 */
				Producto productoBusqueda = productoDao.buscarPorNombre(nombre);
				if(productoBusqueda.getProductoId() == null) {
					/*
					 * Si no existe un producto con el mismo nombre pasa a crear el nuevo producto
					 */
					producto.setProductoNombre(nombre);
					producto.setProductoCantidad(obj.getLong("cantidad"));
					TipoProducto tipoProducto = tipoProductoDao.consultarPorId(obj.getLong("tipoProducto"));
					producto.setProductoTipoProducto(tipoProducto);
					producto.setProductoPrecio(obj.getLong("precio"));
					producto.setProductoFechaRegistro(new Date());
					productoDao.update(producto);
					respuestaGeneral.setTipoRespuesta("0");
					respuestaGeneral.setRespuesta("Nuevo producto registrado con éxito.");
					/*
					 * Se mapea la respuesta con una estructura json en un dato tipo String
					 */
					respuesta = new ObjectMapper().writeValueAsString(respuestaGeneral);
				}else {
					/*
					 * Si existe un producto con el mismo nombre, se devolvera una excepcion,
					 * para indicarle al cliente.
					 */
					respuestaGeneral.setTipoRespuesta("1");
					respuestaGeneral.setRespuesta("Ya existe un producto con el nombre ingresado.");
					/*
					 * Se mapea la respuesta con una estructura json en un dato tipo String
					 */
					respuesta = new ObjectMapper().writeValueAsString(respuestaGeneral);
				}
			}else {
				/*
				 * Se realiza una busqueda del producto registrado para actualizar
				 * para implementar los datos que no van a ser reemplazados ni actualizados
				 */
				Producto productoBuscar = productoDao.buscarPorId(obj.getLong("id"));
				producto.setProductoId(obj.getLong("id"));
				producto.setProductoNombre(obj.getString("nombre"));
				producto.setProductoCantidad(obj.getLong("cantidad"));
				producto.setProductoTipoProducto(productoBuscar.getProductoTipoProducto());
				producto.setProductoPrecio(obj.getLong("precio"));
				producto.setProductoFechaRegistro(productoBuscar.getProductoFechaRegistro());
				productoDao.update(producto);
				respuestaGeneral.setTipoRespuesta("0");
				respuestaGeneral.setRespuesta("Producto actualizado con exito.");
				/*
				 * Se mapea la respuesta con una estructura json en un dato tipo String
				 */
				respuesta = new ObjectMapper().writeValueAsString(respuestaGeneral);
			}
			/*
			 * Se mapea la respuesta con una estructura json en un dato tipo String
			 */
			respuesta = new ObjectMapper().writeValueAsString(respuestaGeneral);
		} catch (Exception e) {
			/*
			 * En caso de un error, este se mostrara en los logs
			 * Sera enviada la respuesta correspondiente al cliente.
			 */
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "ProductoService.agregar, "
					+ ", descripcion: "
					+ e.getMessage());
			respuestaGeneral.setTipoRespuesta("1");
			respuestaGeneral.setRespuesta("Error al ingresar los datos, por favor intente mas tarde.");
			/*
			 * Se mapea la respuesta con una estructura json en un dato tipo String
			 */
			respuesta = new ObjectMapper().writeValueAsString(respuestaGeneral);
		}
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", ProductoService.agregar resultado de agregar/actualizar un producto: " + respuesta);
		return respuesta;
	}
	
	/*
	 * Metodo para listar todos los productos registrados en la base de datos
	 */
	public String listarProductos() throws JsonProcessingException {
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", ProductoService.listarProductos listar todos los productos");
		String respuesta = "";
		RespuestaGeneralDto respuestaGeneral = new RespuestaGeneralDto();
		try {
			/*
			 * Se realiza la busqueda de los productos en la base de datos usando el metodo findAll del productoDao
			 */
			List<Producto> lista = productoDao.findAll();
			if(!lista.isEmpty()) {
				/*
				 * si la lista tiene algun registro, se prosigue con la respuesta con una lista de datos
				 */
				ListaProductosDto listaProductosDto = new ListaProductosDto();
				listaProductosDto.setTipoRespuesta("0");
				listaProductosDto.setListaProductos(lista);
				/*
				 * Se mapea la respuesta con una estructura json en un dato tipo String
				 */
				respuesta = new ObjectMapper().writeValueAsString(listaProductosDto);
			}else {
				/*
				 * si la lista no tiene algun registro
				 * Se envia la respuesta del lista vacia al cliente
				 */
				respuestaGeneral.setTipoRespuesta("1");
				respuestaGeneral.setRespuesta("No hay productos actualmente, por favor intente mas tarde.");
				/*
				 * Se mapea la respuesta con una estructura json en un dato tipo String
				 */
				respuesta = new ObjectMapper().writeValueAsString(respuestaGeneral);
			}
		} catch (Exception e) {
			/*
			 * En caso de un error, este se mostrara en los logs
			 * Sera enviada la respuesta correspondiente al cliente.
			 */
			logger.error(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
					+ "ProductoService.listarProductos, "
					+ ", descripcion: "
					+ e.getMessage());
			respuestaGeneral.setTipoRespuesta("1");
			respuestaGeneral.setRespuesta("Error al listar todos los productos.");
			/*
			 * Se mapea la respuesta con una estructura json en un dato tipo String
			 */
			respuesta = new ObjectMapper().writeValueAsString(respuestaGeneral);
			return respuesta;
		}
  		logger.info(HILO + "[ " + Thread.currentThread().getId()+ " ] " 
  				+ ", ProductoService.listarProductos resultado: " + respuesta);
		return respuesta;
	}

}
