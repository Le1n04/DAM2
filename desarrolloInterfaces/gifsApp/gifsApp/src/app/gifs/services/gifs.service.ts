import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GifsService {

  private _historialEtiquetas: string[] = [];

  get historialEtiquetas() {
    return[...this._historialEtiquetas];
  }

  buscarEtiqueta(etiqueta: string): void {
    const etiquetaOriginal = etiqueta.trim();
    const etiquetaNormalizada = etiquetaOriginal.toLowerCase();
    if (!etiquetaOriginal) return;
    this._historialEtiquetas = this._historialEtiquetas.filter(e => e.toLowerCase() !== etiquetaNormalizada);
    this._historialEtiquetas.unshift(etiquetaOriginal);
    if (this._historialEtiquetas.length > 10) {
      this._historialEtiquetas.pop();
    }
  }
  constructor() { }
}

