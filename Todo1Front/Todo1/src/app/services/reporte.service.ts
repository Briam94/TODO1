import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReporteService {
  
  urlVC = 'http://localhost:8090/kardex/ventaconsolidado/';
  urlP = 'http://localhost:8090/kardex/producto/';
  urlTP = 'http://localhost:8090/kardex/tipoproducto/';
  
  constructor(private http: HttpClient) { }

  getAllVentasConsolidado(){
    return this.http.post(this.urlVC + '/listar', null,
      { responseType: 'text' })
      .pipe(map((response: any) => {
        response = JSON.parse(response);
        return response;
      }, error => error));
  }

  getAllProductos(){
    return this.http.post(this.urlP + '/listar', null,
      { responseType: 'text' })
      .pipe(map((response: any) => {
        response = JSON.parse(response);
        return response;
      }, error => error));
  }
  
  getAllTipoProductos(){
    return this.http.post(this.urlTP + '/listar', null,
      { responseType: 'text' })
      .pipe(map((response: any) => {
        response = JSON.parse(response);
        return response;
      }, error => error));
  }



}
