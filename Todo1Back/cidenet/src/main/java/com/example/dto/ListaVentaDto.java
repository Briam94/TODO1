package com.example.dto;

import java.util.List;

import com.example.entities.Venta;

public class ListaVentaDto {

	private String tipoRespuesta;
	private List<Venta> listaVenta;
	
	
	public String getTipoRespuesta() {
		return tipoRespuesta;
	}
	public void setTipoRespuesta(String tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}
	public List<Venta> getListaVenta() {
		return listaVenta;
	}
	public void setListaVenta(List<Venta> listaVenta) {
		this.listaVenta = listaVenta;
	}
	
	
}
