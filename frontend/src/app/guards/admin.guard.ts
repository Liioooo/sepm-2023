import { CanActivateFn, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { inject } from '@angular/core';

export const adminGuard: CanActivateFn = () => {
  const userService = inject(UserService);
  const router = inject(Router);

  if (userService.isLoggedIn() && userService.getUserRole() === 'ADMIN') {
    return true;
  }

  if (!userService.isLoggedIn()) {
    return router.parseUrl('/login');
  }

  return router.parseUrl('/');
};
