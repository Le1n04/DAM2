import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { tap, map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

export const publicGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.checkAuthentication().pipe(
    tap(isAuthenticated => {
      console.log('Guard Public → isAuthenticated:', isAuthenticated);
      if (isAuthenticated) {
        router.navigate(['/heroes']);
      }
    }),
    map(isAuthenticated => !isAuthenticated),
    catchError((error) => {
      console.error('Error en autenticación:', error);
      return of(true);
    })
    
  );
};
