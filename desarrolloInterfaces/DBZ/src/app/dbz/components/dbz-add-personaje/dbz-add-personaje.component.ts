import { Component, EventEmitter, Output } from '@angular/core';
import { Personaje } from '../../interfaces/personaje.interface';

@Component({
  selector: 'dbz-add-personaje',
  templateUrl: './dbz-add-personaje.component.html',
  styleUrls: ['./dbz-add-personaje.component.css']
})
export class DbzAddPersonajeComponent {

  @Output()
  public onNewPersonaje: EventEmitter<Omit<Personaje, 'id'>> = new EventEmitter();

  public personaje: Omit<Personaje, 'id'> = {
    nombre: '',
    fuerza: 0
  }

  public addPersonaje(): void {
    if (this.personaje.nombre.trim().length === 0) return;

    this.onNewPersonaje.emit(this.personaje);

    this.personaje = { nombre: '', fuerza: 0 };
  }
}