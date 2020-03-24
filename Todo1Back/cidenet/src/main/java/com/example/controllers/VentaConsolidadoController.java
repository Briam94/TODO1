package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.VentaConsolidadoService;
import com.fasterxml.jackson.core.JsonProcessingException;

@CrossOrigin
@RestController
@RequestMapping("kardex")
public class VentaConsolidadoController {

	/*
	 * Instancia de clase VentaConsolidadoService para usar metodos
	 */
	@Autowired
	private VentaConsolidadoService ventaConsolidadoService;
	
	/*
	 * Controlador para listar las ventas consolidadas registradas en la base de datos.
	 */
	@PostMapping(value = "ventaconsolidado/listar" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String registrarVenta() throws JsonProcessingException {
		return ventaConsolidadoService.listarVentaConsolidado();
	}

}
