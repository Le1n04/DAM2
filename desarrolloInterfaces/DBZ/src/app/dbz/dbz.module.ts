import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainPageComponent } from './pages/main-page.component';
import { DbzListaComponent } from './components/dbz-lista/dbz-lista.component';
import { DbzAddPersonajeComponent } from './components/dbz-add-personaje/dbz-add-personaje.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [MainPageComponent, DbzListaComponent, DbzAddPersonajeComponent],
  exports: [MainPageComponent],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class DbzModule { }
