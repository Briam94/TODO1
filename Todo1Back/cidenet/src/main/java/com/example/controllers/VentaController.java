package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.VentaService;
import com.fasterxml.jackson.core.JsonProcessingException;

@CrossOrigin
@RestController
@RequestMapping("kardex")
public class VentaController {

	/*
	 * Instancia de clase VentaService para usar metodos
	 */
	@Autowired
	private VentaService ventaService;

	/*
	 * Controlador para agregar nuevas ventas en la base de datos.
	 */
	@PostMapping(value = "venta/registrar" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String registrarVenta(@RequestBody String trama) throws JsonProcessingException {
		return ventaService.registrarVenta(trama);
	}

	/*
	 * Controlador para listar las ventas registradas en la base de datos.
	 */
	@PostMapping(value = "venta/listar" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String listarVentas() {
		return ventaService.listarVenta();
	}
	
}
