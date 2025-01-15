import { Component } from '@angular/core';
import { GifsService } from 'src/app/gifs/services/gifs.service';
@Component({
  selector: 'shared-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  get history(): string[] {
    return this.gifsService.historialEtiquetas;
  }

  constructor(private gifsService: GifsService) { }

  onSelect(etiqueta: string): void {
    this.gifsService.buscarEtiqueta(etiqueta);
  }
}
