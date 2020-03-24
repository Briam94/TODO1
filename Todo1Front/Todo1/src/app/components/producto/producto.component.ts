import { Component, OnInit } from '@angular/core';
import { ServiciosGeneralesService } from 'src/app/services/servicios-generales.service';
import { ProductoService } from 'src/app/services/producto.service';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css']
})
export class ProductoComponent implements OnInit {

  tipoProductos = [];
  nombre = '';
  cantidad = 1;
  tipoProducto = 0;
  precio = 0;

  listaProductos = [];
  editar = false;

  idEditar
  nombreEditar;
  cantidadEditar;
  tipoProductoEditar;
  precioEditar;

  nuevoTipoProducto = false;
  nombreTipoProducto;

  constructor(public serviciosGenerales: ServiciosGeneralesService, private service: ProductoService) { }

  ngOnInit(): void {
    const getTipoProductos = this.service.getTipoProducto().subscribe(data => {
      data.lista.forEach(element => {
        this.tipoProductos.push({
          tipoProductoId: element.tipoProductoId,
          tipoProducto: element.tipoProducto.toUpperCase()
        }) 
      });
      getTipoProductos.unsubscribe();
    })

    const getProductos = this.service.getAllProduct().subscribe(data => {
      this.listaProductos = data.listaProductos;
      getProductos.unsubscribe();
    })
  }

  guardar(){
    if(this.nombre === ''  || this.tipoProducto === 0){
      alert('Todos los campos son obligatorios');
    }else {
      if(this.cantidad < 0){
        alert('La cantida de productos debe ser mayor o igual a 1');
      }else {
        const precioNumber = +this.precio;
        if(precioNumber <= 100){
          alert('El precio del producto debe ser mayor o igual a $100');
        }else {
          if(this.tipoProducto == 0){
            alert('Seleccione un tipo de producto valido');
          }else {
            const tipoProducto: number = +this.tipoProducto;
            const datos = {
              use: '0',
              nombre: this.nombre,
              cantidad: this.cantidad,
              tipoProducto: tipoProducto,
              precio: this.precio 
            }
            const agregarProducto = this.service.addProducto(datos).subscribe(data => {
              const comparar: string = data.tipoRespuesta;
              if(comparar == '0'){
                alert(data.respuesta);
                this.limpiar();
                agregarProducto.unsubscribe();
              }else {
                alert(data.respuesta);
                agregarProducto.unsubscribe();
              }
            }, error => {
                alert('No ha sido posible guardar el nuevo producto, por favor intente mas tarde.');
                agregarProducto.unsubscribe();
            });
          }
        }
      }
    }
  }

  limpiar(){
    this.nombre = '';
    this.cantidad = 1;
    this.tipoProducto = 0;
    this.precio = 0;
  }

  iniciarEditar(producto){
    console.log('producto:', producto);
    this.editar = true;
    
    this.idEditar = producto.productoId;
    this.nombreEditar = producto.productoNombre;
    this.cantidadEditar = producto.productoCantidad;
    this.precioEditar = producto.productoPrecio;
    this.tipoProductoEditar = {
      tipoProductoId: producto.productoTipoProducto.tipoProductoId,
      tipoProducto: producto.productoTipoProducto.tipoProducto
    };
  }

  actualizar(){
    if(this.cantidadEditar >= 0){
      const tipoProducto: number = +this.tipoProducto;
      const datos = {
        use: '1',
        id: this.idEditar,
        nombre: this.nombreEditar,
        cantidad: this.cantidadEditar,
        tipoProducto: this.tipoProductoEditar.tipoProductoId,
        precio: this.precioEditar 
      }
      const actualizarProducto = this.service.addProducto(datos).subscribe(data => {
        console.log('data:', data);
        const comparar: string = data.tipoRespuesta;
        if(comparar == '0'){
          alert(data.respuesta);
          const getProductos = this.service.getAllProduct().subscribe(data => {
            this.listaProductos = data.listaProductos;
            getProductos.unsubscribe();
          })
          this.cancelar();
          actualizarProducto.unsubscribe();
        }else {
          alert(data.respuesta);
          actualizarProducto.unsubscribe();
        }
      }, error => {
          alert('No ha sido posible actualizar el producto seleccionado, por favor intente mas tarde.');
          actualizarProducto.unsubscribe();
      });
    }
  }

  cancelar(){
    this.editar = false;
    this.idEditar = '';
    this.nombreEditar = '';
    this.cantidadEditar = '';
    this.precioEditar = '';
    this.tipoProductoEditar = '';
  }

  iniciarCrearTipoProducto(){
    console.log('nuevo tipo producto');
    this.nuevoTipoProducto = true;
  }

  crearTipoProducto(){
    const datos = {
      tipoProducto: this.nombreTipoProducto
    }
    const addTipoProducto = this.service.addTipoProducto(datos).subscribe(data => {
      const comparar: string = data.tipoRespuesta;
      if(comparar == '0'){
        alert(data.respuesta);
        const getTipoProductos = this.service.getTipoProducto().subscribe(data => {
          this.tipoProductos = [];
          data.lista.forEach(element => {
            this.tipoProductos.push({
              tipoProductoId: element.tipoProductoId,
              tipoProducto: element.tipoProducto.toUpperCase()
            }) 
          });
          this.cancelarTipoProducto();
          getTipoProductos.unsubscribe();
        });
        addTipoProducto.unsubscribe();
      }else {
        alert(data.respuesta);
        addTipoProducto.unsubscribe();
      }
    }, error => {
        alert('No ha sido posible guardar el nuevo tipo de producto, por favor intente mas tarde.');
        addTipoProducto.unsubscribe();
    });
  }

  cancelarTipoProducto(){
    this.nuevoTipoProducto = false
    this.nombreTipoProducto = '';
  }

}
