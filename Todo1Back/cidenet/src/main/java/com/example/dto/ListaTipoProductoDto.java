package com.example.dto;

import java.util.List;

import com.example.entities.TipoProducto;

public class ListaTipoProductoDto {

	private String tipoRespuesta;
	private List<TipoProducto> lista;
	public String getTipoRespuesta() {
		return tipoRespuesta;
	}
	public void setTipoRespuesta(String tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}
	public List<TipoProducto> getLista() {
		return lista;
	}
	public void setLista(List<TipoProducto> lista) {
		this.lista = lista;
	}
	
	

}
