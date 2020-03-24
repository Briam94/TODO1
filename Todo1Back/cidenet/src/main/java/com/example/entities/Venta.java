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
@Table(name = "venta" ,schema="kardex")
@XmlRootElement
@NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")
@NamedQuery(name = "Venta.findById", query = "SELECT v FROM Venta v WHERE v.ventaId = :Id")
@NamedQuery(name = "Venta.findByAll", query = "SELECT v FROM Venta v WHERE v.ventaDocumento = :documento AND v.ventaFecha = :fechaVenta AND v.ventaTotalPagar = :total")
 
public class Venta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "VENTA_VENTAID_GENERATOR",
	sequenceName = "kardex.venta_sec", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "VENTA_VENTAID_GENERATOR")
	@Basic(optional = false)
	@NotNull
	@Column(name = "venta_id")
	private Long ventaId;

	@Column(name = "venta_cantidad")
	private Long ventaCantidad;
	
	@Column(name = "venta_total_pagar")
	private Long ventaTotalPagar;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = " venta_fecha")
	private Date ventaFecha;

	@Column(name = "venta_documento")
	private String ventaDocumento;

	public Long getVentaId() {
		return ventaId;
	}

	public void setVentaId(Long ventaId) {
		this.ventaId = ventaId;
	}

	public Long getVentaCantidad() {
		return ventaCantidad;
	}

	public void setVentaCantidad(Long ventaCantidad) {
		this.ventaCantidad = ventaCantidad;
	}

	public Long getVentaTotalPagar() {
		return ventaTotalPagar;
	}

	public void setVentaTotalPagar(Long ventaTotalPagar) {
		this.ventaTotalPagar = ventaTotalPagar;
	}

	public Date getVentaFecha() {
		return ventaFecha;
	}

	public void setVentaFecha(Date ventaFecha) {
		this.ventaFecha = ventaFecha;
	}

	public String getVentaDocumento() {
		return ventaDocumento;
	}

	public void setVentaDocumento(String ventaDocumento) {
		this.ventaDocumento = ventaDocumento;
	}
	
}
