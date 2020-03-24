package com.example.dto;

import java.util.List;

import com.example.entities.VentaConsolidado;

public class ListaVentaConsolidadoDto {
	
	private String tipoRespuesta;
	private List<VentaConsolidado> ventaConsolidado;
	
	
	public String getTipoRespuesta() {
		return tipoRespuesta;
	}
	public void setTipoRespuesta(String tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}
	public List<VentaConsolidado> getVentaConsolidado() {
		return ventaConsolidado;
	}
	public void setVentaConsolidado(List<VentaConsolidado> ventaConsolidado) {
		this.ventaConsolidado = ventaConsolidado;
	}

}
