package com.example.dto;

import java.util.List;import com.example.entities.Producto;

public class ListaProductosDto {
	
	private String tipoRespuesta;
	private List<Producto> listaProductos;
	
	
	public String getTipoRespuesta() {
		return tipoRespuesta;
	}
	public void setTipoRespuesta(String tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}
	public List<Producto> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

}
