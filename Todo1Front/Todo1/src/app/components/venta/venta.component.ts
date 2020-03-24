import { Component, OnInit } from '@angular/core';
import { ProductoService } from 'src/app/services/producto.service';
import { VentaService } from 'src/app/services/venta.service';

@Component({
  selector: 'app-venta',
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.css']
})
export class VentaComponent implements OnInit {

  productos = [];
  carritoCompras = [];
  verCarritoCompras = false;
  documento = '';
  total = 0;

  constructor(private service: ProductoService, private ventaService: VentaService) { }

  ngOnInit(): void {
    const getAllProducts = this.service.getAllProduct().subscribe(data => {
      console.log('data:', data);
      this.productos = data.listaProductos;
      console.log('this.productos:', this.productos);
      getAllProducts.unsubscribe()
    })
  }

  agregarAlCarrito(producto){
    console.log('producto:', producto);
    if(this.carritoCompras.length > 0){
      const index = this.carritoCompras.indexOf(producto);
      console.log('index:', index);
      if(index !== -1){
        this.carritoCompras[index].productoCantidad = this.carritoCompras[index].productoCantidad + 1;
      }else {
        producto.productoCantidad = 1;
        this.carritoCompras.push(producto);
      }
    }else {
      producto.productoCantidad = 1;
      this.carritoCompras.push(producto);
    }
    console.log('this.carritoCompras:', this.carritoCompras);
    console.log('this.productos:', this.productos);
    this.total = 0;
    this.carritoCompras.forEach(element => {
      this.total = this.total + (element.productoPrecio * element.productoCantidad);
    });
  }

  eliminarCarrito(producto){
    const entrante = producto;
    const index = this.carritoCompras.findIndex((carritoCompras) => entrante == carritoCompras);
    if(this.carritoCompras[index].productoCantidad > 1){
      this.carritoCompras[index].productoCantidad = this.carritoCompras[index].productoCantidad - 1;
    }else {
      this.carritoCompras.splice(index, 1);
    }
    console.log('this.carritoCompras:', this.carritoCompras);
    console.log('this.productos:', this.productos);
  }

  finalizarVenta(){
    if(this.documento != ''){
      if(confirm('Desea finalizar la venta?')){
        let costoTotal = 0;
        let cantidadTotal = 0;
        this.carritoCompras.forEach(element => {
          costoTotal = costoTotal + element.productoPrecio;
          cantidadTotal = cantidadTotal + element.productoCantidad;
        });
        const datos = {
          ventas: this.carritoCompras,
          documento: this.documento,
          totalPagar: this.total,
          cantidadTotal: cantidadTotal
        }
        console.log('datos:', datos);
        const agregarVenta = this.ventaService.registrarVenta(datos).subscribe(data => {
          const comparar: string = data.tipoRespuesta;
          if(comparar == '0'){
            alert(data.respuesta);
            this.carritoCompras = [];
            this.verCarritoCompras = false;
            agregarVenta.unsubscribe();
          }else {
            alert(data.respuesta);
            agregarVenta.unsubscribe();
          }
        }, error => {
            alert('No ha sido posible guardar el nuevo producto, por favor intente mas tarde.');
            agregarVenta.unsubscribe();
        });
      }
    }else {
      alert('El numero de documento de identificacion del cliente es obligatorio');
    }
  }

}
