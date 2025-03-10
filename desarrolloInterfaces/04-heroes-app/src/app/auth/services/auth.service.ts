import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../interfaces/user';
import { environments } from 'src/environments/environments';
import { Observable, of, tap } from 'rxjs';
import { map } from 'rxjs';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API_URL = environments.baseUrl;
  private user?: User;

  get currentUser(): User | undefined {
    if (!this.user) {
      return undefined;
    }

    return structuredClone(this.user);
  }

  login (email : string, password: string): Observable<User> {
    return this.http.get<User>(`${this.API_URL}/users/1`).pipe(
      tap(user => this.user = user),
      tap(user => localStorage.setItem('token', 'asdSDwdasDda123sASw4fsdfuhj2'))
    );
  }

  logout() {
    this.user = undefined;
    localStorage.clear();
  }

  checkAuthentication(): Observable<boolean> {
    const token = localStorage.getItem('token');
  
    if (!token) return of(false); // ✅ Convertimos el boolean a Observable
  
    return this.http.get<User>(`${this.API_URL}/users/1`).pipe(
      tap(user => this.user = user),
      map(user => !!user), // ✅ Si hay usuario → true, si no → false
      catchError(() => of(false)) // ✅ Si hay error → false
    );
  }
  

  constructor(private http: HttpClient) { }
}
