import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ServiciosGeneralesService {


  constructor() { }



  
  validarCaracter(e: any, tipo: string) {
    const tecla = document.all ? e.keyCode : e.which;
    // Tecla de retroceso para borrar, siempre la permite
    if (tecla === 8) {
        return true;
    }

    let patron;
    switch (tipo) {
        case 'ALF':
            patron = /[A-Za-z0-9]/;
            break;
        case 'NUM':
            patron = /[0-9]/;
            break;
        case 'SERV':
            patron = /[0-9.]/;
            break;
        case 'SER':
            patron = /[0-9.-]/;
            break;
        case 'TEX':
            patron = /[A-Za-z]/;
            break;
        case 'DIR': // para direcciones
            patron = /[A-Za-z0-9#°\- ]/;
            break;
        case 'RAZ': // para razon social ya que permite punto
            patron = /[A-Za-z0-9. ]/;
            break;
        case 'PAS': // se valida que la clave contenga caracteres validos configurados en bd
            patron = /[A-Za-z0-9\\/:*=-¡!.]/;
            break;
        case 'ESP': // texto con espacio
            patron = /[A-Za-z ]/;
            break;
        case 'ESPNOM': // texto con espacio que permite números
            patron = /[A-Za-z0-9. ]/;
            break;
        case 'DECI':
            patron = /[0-9.]/;
            break;
        case 'NIT':
            patron = /[0-9\-]/;
            break;
        case 'EMAIL':
            patron = /[A-Za-z0-9.@_\-]/;
            break;
        case 'TEXEVENT':
            patron = /[A-Za-z0-9\-. ]/;
            break;
        case 'ASIG': // para separador de asignacion de boletas
            patron = /[A-Za-z0-9#°\-; ]/;
            break;
    }
    return patron.test(String.fromCharCode(tecla));
}

}
