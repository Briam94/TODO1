package com.example.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tipo_producto" , schema="kardex")
@XmlRootElement
@NamedQuery(name = "TipoProducto.findAll", query = "SELECT tp FROM TipoProducto tp")
@NamedQuery(name = "TipoProducto.findById", query = "SELECT tp FROM TipoProducto tp WHERE tp.tipoProductoId = :Id")
@NamedQuery(name = "TipoProducto.findByName", query = "SELECT tp FROM TipoProducto tp WHERE tp.tipoProducto = :nombre")
public class TipoProducto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "TIPOPRODUCTO_TIPOPRODUCTOID_GENERATOR",
	sequenceName = "kardex.tipo_producto_sec", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TIPOPRODUCTO_TIPOPRODUCTOID_GENERATOR")
	@Basic(optional = false)
	@NotNull
	@Column(name = "tipo_producto_id")
	private Long tipoProductoId;

	@Column(name = "tipo_producto")
	private String 	tipoProducto;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = " tipo_producto_fecha_creacion")
	private Date tipoProductoFechaCreacion;
	
	public Date getTipoProductoFechaCreacion() {
		return tipoProductoFechaCreacion;
	}

	public void setTipoProductoFechaCreacion(Date tipoProductoFechaCreacion) {
		this.tipoProductoFechaCreacion = tipoProductoFechaCreacion;
	}

	public Long getTipoProductoId() {
		return tipoProductoId;
	}

	public void setTipoProductoId(Long tipoProductoId) {
		this.tipoProductoId = tipoProductoId;
	}

	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
}
