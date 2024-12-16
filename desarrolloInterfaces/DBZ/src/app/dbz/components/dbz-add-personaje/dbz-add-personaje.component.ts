import { Component, EventEmitter, Output } from '@angular/core';
import { Personaje } from '../../interfaces/personaje.interface';

@Component({
  selector: 'dbz-add-personaje',
  templateUrl: './dbz-add-personaje.component.html',
  styleUrls: ['./dbz-add-personaje.component.css']
})
export class DbzAddPersonajeComponent {

  @Output()
  public onNewPersonaje: EventEmitter<Personaje> = new EventEmitter();

  public personaje : Personaje = {
    nombre: '',
    fuerza: 0
  }

  public addPersonaje(): void {
    //debugger;
    console.log(this.personaje);
    if (this.personaje.nombre.length === 0) return;
    this.onNewPersonaje.emit(this.personaje);
    this.personaje={ nombre: '', fuerza: 0 };
  }
}




