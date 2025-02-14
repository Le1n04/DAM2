import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SearchResponse } from '../interfaces/gifs.interfaces';
import { Gif } from '../interfaces/gifs.interfaces';

@Injectable({
  providedIn: 'root'
})
export class GifsService {
  public listadoGifs: Gif[] = [];

  private apiKey: string = 'z4qe0qv5VcdkKgevSGBrFp1tVDwzCzoT';
  private serviceUrl: string = 'https://api.giphy.com/v1/gifs'
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

    const params = new HttpParams()
      .set('api_key', this.apiKey)
      .set('limit', 10)
      .set('q', etiqueta);

    this.http.get<SearchResponse>(`${this.serviceUrl}/search`, {params}).subscribe( resp => {
      this.listadoGifs = resp.data;
      console.log({gifs: this.listadoGifs});
    })
    this.almacenarLocalStorage();
  }

  private almacenarLocalStorage(): void {
    localStorage.setItem('historial', JSON.stringify(this._historialEtiquetas));
  }

  private cargarLocalStorage(): void {
    if (!localStorage.getItem('historial')) return;
    this._historialEtiquetas = JSON.parse(localStorage.getItem('historial')!);
    if (this._historialEtiquetas.length === 0) return;
    this.buscarEtiqueta(this._historialEtiquetas[0]);
  }



  constructor( private http: HttpClient) {
    this.cargarLocalStorage();
  }
}

