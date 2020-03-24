package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.TipoProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/kardex")
@CrossOrigin
public class TipoProductoControllers {

	/*
	 * Instancia de clase TipoProductoService para usar metodos
	 */
	@Autowired
	private TipoProductoService tipoProductoService;

	/*
	 * Controlador para listar los tipos de productos registrados en la base de datos
	 */
	@PostMapping(value = "tipoproducto/listar" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String listarTipoProducto() {
		return tipoProductoService.listaTipoProducto();
	}

	/*
	 * Controlador para agregar un nuevo tipo producto
	 */
	@PostMapping(value = "tipoproducto/agregar" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String listarTipoProducto(@RequestBody String trama) throws JsonProcessingException {
		return tipoProductoService.agregar(trama);
	}


}
