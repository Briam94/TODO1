import { Component, OnInit } from '@angular/core';
import { ReporteService } from 'src/app/services/reporte.service';
import { DatePipe } from '@angular/common';
import { Angular5Csv } from 'angular5-csv/dist/Angular5-csv';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css']
})
export class ReportesComponent implements OnInit {

  ventasConsolidado = [];
  documento = '';
  descripcion = false;
  descripcionCompra;

  optionsReporteGeneral = {
    fieldSeparator: ';',
    quoteStrings: '',
    decimalseparator: '.',
    headers: ['FECHA', 'DOCUMENTO CLIENTE', 'CANTIDAD TOTAL DE PRODUCTOS', 'TOTAL A PAGAR'],
    showTitle: false,
    useBom: false,
    removeNewLines: false,
    keys: [],
    showLabels: false,
    noDownload: false,
    nullToEmptyString: true
  };

  optionsReporteVentaDetallado = {
    fieldSeparator: ';',
    quoteStrings: '',
    decimalseparator: '.',
    headers: ['FECHA', 'DOCUMENTO CLIENTE', 'PRODUCTO', 'CANTIDAD', 'PRECIO UNITARIO', 'TOTAL'],
    showTitle: false,
    useBom: false,
    removeNewLines: false,
    keys: [],
    showLabels: false,
    noDownload: false,
    nullToEmptyString: true
  };

  optionsProductos = {
    fieldSeparator: ';',
    quoteStrings: '',
    decimalseparator: '.',
    headers: ['PRODUCTO', 'CANTIDAD EN STOCK', 'PRECIO UNITARIO', 'TIPO DE PRODUCTO'],
    showTitle: false,
    useBom: false,
    removeNewLines: false,
    keys: [],
    showLabels: false,
    noDownload: false,
    nullToEmptyString: true
  };

  optionsTipoProducto = {
    fieldSeparator: ';',
    quoteStrings: '',
    decimalseparator: '.',
    headers: ['TIPO PRODUCTO'],
    showTitle: false,
    useBom: false,
    removeNewLines: false,
    keys: [],
    showLabels: false,
    noDownload: false,
    nullToEmptyString: true
  };

  constructor(private service: ReporteService, public datePipe: DatePipe) { }

  ngOnInit(): void {
    const getAllVentasConsolidado = this.service.getAllVentasConsolidado().subscribe(data => {
      if(data.length > 0){
        console.log('data:', data);
        data.forEach(element1 => {
          let productos = [];
          data.forEach(element2 => {
            if(element1.ventaConsolidadoVentaId.ventaId === element2.ventaConsolidadoVentaId.ventaId){
              productos.push({
                cantidad: element2.ventaConsolidadoCantidad,
                nombre: element2.ventaConsolidadoProductoId.productoNombre,
                precio: element2.ventaConsolidadoProductoId.productoPrecio
              })
            }
          });
          const fecha = this.datePipe.transform(element1.ventaConsolidadoVentaId.ventaFecha, 'dd/MM/yyyy HH:mm');
          this.ventasConsolidado.push({
            venta:{
              ventaId: element1.ventaConsolidadoVentaId.ventaId,
              cantidadTotalProductos: element1.ventaConsolidadoVentaId.ventaCantidad,
              totalPagar: element1.ventaConsolidadoVentaId.ventaTotalPagar,
              fechaVenta: fecha,
              documento: element1.ventaConsolidadoVentaId.ventaDocumento,
              productos
            }
          })
        });
        this.ventasConsolidado.forEach(element => {
          const index = this.ventasConsolidado.indexOf(element);
          this.ventasConsolidado.splice(index, 1);
        });
      }
      console.log('this.ventasConsolidado:', this.ventasConsolidado);
      getAllVentasConsolidado.unsubscribe();
    })
  }

  verDetalle(dato){
    console.log('dato:', dato);
    this.descripcion = true;
    this.descripcionCompra = dato;
  }

  reporteProductos(){
    let productos = [];
    const getAllProductos = this.service.getAllProductos().subscribe(data => {
      console.log('data:', data);
      const comparar: string = data.tipoRespuesta;
      if(comparar == '0'){
        data.listaProductos.forEach(element => {
            productos.push({
              productoNombre: element.productoNombre,
              productoCantidad: element.productoCantidad,
              productoPrecio: element.productoPrecio,
              tipoProducto: element.productoTipoProducto.tipoProducto
            })
          });
          this.generarCSV(productos, this.optionsProductos)
          getAllProductos.unsubscribe();
      }else {
          alert(data.respuesta);
          getAllProductos.unsubscribe();
        }
      }, error => {
          alert('No ha sido posible guardar el nuevo producto, porfavor intente mas tarde.');
          getAllProductos.unsubscribe();
      });
      console.log('productos:', productos);
  }

  reportVentaeGeneral(){
    let ventas = [];
    this.ventasConsolidado.forEach(element => {
      ventas.push({
        fecha: element.venta.fechaVenta,
        documento: element.venta.documento,
        cantidadTotalProductos: element.venta.cantidadTotalProductos,
        totalPagar: element.venta.totalPagar
      })
    });
    console.log('ventas:', ventas);
    this.generarCSV(ventas, this.optionsReporteGeneral);
  }

  reporteVentaDetallado(){
    let ventasDetallado = [];
    this.ventasConsolidado.forEach(element => {
      let productosLista = [];
      element.venta.productos.forEach(productos => {
        productosLista.push({
          producto: productos.nombre,
          cantidad: productos.cantidad,
          precio: productos.precio,
          total: productos.cantidad * productos.precio
        })
      });
      productosLista.forEach(productoList => {
        ventasDetallado.push({
          fecha: element.venta.fechaVenta,
          documento: element.venta.documento,
          producto: productoList.producto,
          cantidad: productoList.cantidad,
          precio: productoList.precio,
          total: productoList.total
        })
      });
    });
    this.generarCSV(ventasDetallado, this.optionsReporteVentaDetallado)
    console.log('ventasDetallado:', ventasDetallado);
  }

  reporteTipoProducto(){
    let tipoProducto = [];
    const getAllTipoProducto = this.service.getAllTipoProductos().subscribe(data => {
      console.log('data:', data);
      const comparar: string = data.tipoRespuesta;
      if(comparar == '0'){
        data.lista.forEach(element => {
          tipoProducto.push({
            tipoProducto: element.tipoProducto
          });
        });
        this.generarCSV(tipoProducto, this.optionsTipoProducto)
        getAllTipoProducto.unsubscribe();
      }else {
        alert(data.respuesta);
        getAllTipoProducto.unsubscribe();
      }
    }, error => {
        alert('No ha sido posible guardar el nuevo producto, porfavor intente mas tarde.');
        getAllTipoProducto.unsubscribe();
    });
  }

  generarCSV(datos, options) {
    const fecha = this.datePipe.transform(Date.now(), 'dd/MM/yyyy hh:mm');
    new Angular5Csv(datos, 'reporte general de ventas descargado en la fecha:  ' + fecha, options);
  }

}
