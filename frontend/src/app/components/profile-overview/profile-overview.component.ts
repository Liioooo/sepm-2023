import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-overview',
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.scss']
})
export class ProfileOverviewComponent {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  get userDetails$() {
    return this.authService.userDetails$;
  }

  logoutUser() {
    if (this.authService.isLoggedIn()) {
      this.authService.logoutUser();
      this.router.navigate(['/']);
    }
  }
}
