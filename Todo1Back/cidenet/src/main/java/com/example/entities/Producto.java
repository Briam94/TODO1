package com.example.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "producto" ,schema="kardex")
@XmlRootElement
@NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
@NamedQuery(name = "Producto.findById", query = "SELECT p FROM Producto p WHERE p.productoId = :id")
@NamedQuery(name = "Producto.findByName", query = "SELECT p FROM Producto p WHERE p.productoNombre = :name")
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "PRODUCTO_PRODUCTOID_GENERATOR",
	sequenceName = "kardex.producto_sec", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUCTO_PRODUCTOID_GENERATOR")
	@Basic(optional = false)
	@NotNull
	@Column(name = "producto_id")
	private Long productoId;
	
	@Column(name = "producto_nombre")
	private String productoNombre;
	
	@Column(name = "producto_cantidad")
	private Long productoCantidad;
	
	@Column(name = "producto_precio")
	private Long productoPrecio;

    @JoinColumn(name = "producto_tipo_producto", referencedColumnName = "tipo_producto_id")
    @ManyToOne
    private TipoProducto productoTipoProducto;
    

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = " producto_fecha_registro")
	private Date productoFechaRegistro;

	public Date getProductoFechaRegistro() {
		return productoFechaRegistro;
	}

	public void setProductoFechaRegistro(Date productoFechaRegistro) {
		this.productoFechaRegistro = productoFechaRegistro;
	}

	public Long getProductoId() {
		return productoId;
	}

	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}

	public String getProductoNombre() {
		return productoNombre;
	}

	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}

	public Long getProductoCantidad() {
		return productoCantidad;
	}

	public void setProductoCantidad(Long productoCantidad) {
		this.productoCantidad = productoCantidad;
	}

	public Long getProductoPrecio() {
		return productoPrecio;
	}

	public void setProductoPrecio(Long productoPrecio) {
		this.productoPrecio = productoPrecio;
	}

	public TipoProducto getProductoTipoProducto() {
		return productoTipoProducto;
	}

	public void setProductoTipoProducto(TipoProducto productoTipoProducto) {
		this.productoTipoProducto = productoTipoProducto;
	}
    
    
}
