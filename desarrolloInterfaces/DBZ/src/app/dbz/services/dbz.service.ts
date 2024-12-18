import { Injectable } from '@angular/core';
import { Personaje } from '../interfaces/personaje.interface';
import { v4 as uuid } from 'uuid';

@Injectable({ providedIn: 'root' })
export class DbzService {
    constructor() { }

    public personajes: Personaje[] = [
        {
            id: uuid(), nombre: 'Krilin', fuerza: 500
        },
        {
            id: uuid(), nombre: 'Tenshinhan', fuerza: 600
        }
    ];

    public onNewPersonaje(personaje: Omit<Personaje, 'id'>): void {
        const newPersonaje: Personaje = { id: uuid(), ...personaje };
        this.personajes.push(newPersonaje);
    }

    public eliminarPersonaje(id: string): void {
        this.personajes = this.personajes.filter(personaje => personaje.id !== id);
    }

    ngOnInit() {

    }
}