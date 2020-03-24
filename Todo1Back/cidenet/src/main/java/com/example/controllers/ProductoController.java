package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.ProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;

@CrossOrigin
@RestController
@RequestMapping("kardex")
public class ProductoController {
	
	/*
	 * Instancia de clase ProductoService para usar metodos
	 */
	@Autowired
	private ProductoService productoService;

	/*
	 * Controlador para listar los productos registrados en la base de datos
	 */
	@PostMapping(value = "producto/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public String listarProductos() throws JsonProcessingException {
		return productoService.listarProductos();
	}

	/*
	 * Controlador para agregar o actualizar un producto.
	 */
	@PostMapping(value = "producto/agregar", produces = MediaType.APPLICATION_JSON_VALUE)
	public String agregar(@RequestBody String trama) throws JsonProcessingException {
		return productoService.agregar(trama);
	}


}
