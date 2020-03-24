package com.example.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "venta_consolidado" ,schema="kardex")
public class VentaConsolidado implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "VENTACONSOLIDADO_VENTACONSOLIDADOID_GENERATOR",
	sequenceName = "kardex.tipo_producto_sec", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "VENTACONSOLIDADO_VENTACONSOLIDADOID_GENERATOR")
	@Basic(optional = false)
	@NotNull
	@Column(name = "venta_consolidado_id")
	private Long ventaConsolidadoId;
	
	@JoinColumn(name = "venta_consolidado_venta_id", referencedColumnName = "venta_id")
	@ManyToOne
	private Venta ventaConsolidadoVentaId;

	@JoinColumn(name = "venta_consolidado_producto_id", referencedColumnName = "producto_id")
	@ManyToOne
	private Producto ventaConsolidadoProductoId;
	
	@Column(name = "venta_consolidado_cantidad")
	private Long ventaConsolidadoCantidad;

	public Long getVentaConsolidadoId() {
		return ventaConsolidadoId;
	}

	public void setVentaConsolidadoId(Long ventaConsolidadoId) {
		this.ventaConsolidadoId = ventaConsolidadoId;
	}

	public Long getVentaConsolidadoCantidad() {
		return ventaConsolidadoCantidad;
	}

	public void setVentaConsolidadoCantidad(Long ventaConsolidadoCantidad) {
		this.ventaConsolidadoCantidad = ventaConsolidadoCantidad;
	}

	public Venta getVentaConsolidadoVentaId() {
		return ventaConsolidadoVentaId;
	}

	public void setVentaConsolidadoVentaId(Venta ventaConsolidadoVentaId) {
		this.ventaConsolidadoVentaId = ventaConsolidadoVentaId;
	}

	public Producto getVentaConsolidadoProductoId() {
		return ventaConsolidadoProductoId;
	}

	public void setVentaConsolidadoProductoId(Producto ventaConsolidadoProductoId) {
		this.ventaConsolidadoProductoId = ventaConsolidadoProductoId;
	}
	
	

}
