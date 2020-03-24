import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class VentaService {

  url = 'http://localhost:8090/kardex/venta';
  
  constructor(private http: HttpClient) { }

  registrarVenta(datos){
    return this.http.post(this.url + '/registrar', datos,
      { responseType: 'text' })
      .pipe(map((response: any) => {
        response = JSON.parse(response);
        return response;
      }, error => error));
  }
}
