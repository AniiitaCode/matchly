import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { catchError, firstValueFrom, map, of } from 'rxjs';

export const authGuard: CanActivateFn = async (route: ActivatedRouteSnapshot) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const publicPages = ['', 'login', 'register'];
  const currentPath = route.routeConfig?.path || '';

  const isLoggedIn = await firstValueFrom(
    authService.restoreUser().pipe(
      map(() => authService.isLoggedIn()),
      catchError(() => of(false))
    )
  );

  if (isLoggedIn && publicPages.includes(currentPath)) {
    return router.parseUrl('/home');
  }

  if (!isLoggedIn && !publicPages.includes(currentPath)) {
    return router.parseUrl('/login');
  }

  return true;

};
