import { Injectable } from '@angular/core';
import { Personaje } from '../interfaces/personaje.interface';

@Injectable({providedIn: 'root'})
export class DbzService {
    constructor() { }
    
    public personajes: Personaje[] =
        [{
            nombre: 'Krilin', fuerza : 500
        }, {
            nombre: 'Tenshinhan', fuerza : 600
        }]
    
        public onNewPersonaje(personaje :Personaje): void {
            this.personajes.push(personaje);
        }
    
        public eliminarPersonaje(index : number): void {
            this.personajes.splice(index, 1);
        }

        ngOnInit() {
        
        }
}