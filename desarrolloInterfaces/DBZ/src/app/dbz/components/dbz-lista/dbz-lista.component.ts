import { Component, Input } from '@angular/core';
import { Personaje } from '../../interfaces/personaje.interface';

@Component({
  selector: 'dbz-lista',
  templateUrl: './dbz-lista.component.html',
  styleUrls: ['./dbz-lista.component.css']
})
export class DbzListaComponent {
  @Input('miLista')
  public listaPersonajes: Personaje[] = 
  [{
    nombre: 'Trunks', fuerza : 7000
  }]
}
