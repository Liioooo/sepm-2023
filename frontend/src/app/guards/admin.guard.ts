import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';

export const adminGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn() && authService.getUserRole() === 'ADMIN') {
    return true;
  }

  if (!authService.isLoggedIn()) {
    return router.parseUrl('/login');
  }

  return router.parseUrl('/');
};
