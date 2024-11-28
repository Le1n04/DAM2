import {Component, OnInit} from '@angular/core';
import { Personaje } from '../interfaces/personaje.interface';

@Component({
    selector: 'app-dbz-main-page',
    templateUrl: './main-page.component.html'
})

export class MainPageComponent implements OnInit {
    constructor() { }
    public personajes: Personaje[] =
    [{
        nombre: 'Krilin', fuerza : 500
    }, {
        nombre: 'Tenshinhan', fuerza : 600
    }]
    ngOnInit() {
        
    }
}