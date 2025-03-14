import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DbzModule } from './dbz/dbz.module';
import { DbzListaComponent } from './dbz/components/dbz-lista/dbz-lista.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [DbzModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'DBZ';
}
