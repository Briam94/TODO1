import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  
  urlTP = 'http://localhost:8090/kardex/tipoproducto';
  urlP = 'http://localhost:8090/kardex/producto';
  
  constructor(private http: HttpClient) { }

  getTipoProducto(){
    return this.http.post(this.urlTP + '/listar', null,
      { responseType: 'text' })
      .pipe(map((response: any) => {
        response = JSON.parse(response);
        return response;
      }, error => error));
  }

  addProducto(datos){
    return this.http.post(this.urlP + '/agregar', datos,
      { responseType: 'text' })
      .pipe(map((response: any) => {
        response = JSON.parse(response);
        return response;
      }, error => error));
  }

  getAllProduct(){
    return this.http.post(this.urlP + '/listar', null,
      { responseType: 'text' })
      .pipe(map((response: any) => {
        response = JSON.parse(response);
        return response;
      }, error => error));
  }

  addTipoProducto(datos){
    return this.http.post(this.urlTP + '/agregar', datos,
      { responseType: 'text' })
      .pipe(map((response: any) => {
        response = JSON.parse(response);
        return response;
      }, error => error));
  }

}
