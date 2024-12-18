import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Personaje } from '../../interfaces/personaje.interface';

@Component({
  selector: 'dbz-lista',
  templateUrl: './dbz-lista.component.html',
  styleUrls: ['./dbz-lista.component.css']
})
export class DbzListaComponent {
  @Input('miLista')
  public listaPersonajes: Personaje[] = [{
    id: '1', nombre: 'Trunks', fuerza: 7000
  }];

  @Output()
  public onEliminarPersonaje = new EventEmitter<string>();

  eliminarPersonaje(id: string): void {
    this.onEliminarPersonaje.emit(id);
  }
}
