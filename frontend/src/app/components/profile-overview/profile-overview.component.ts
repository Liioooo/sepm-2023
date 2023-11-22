import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-overview',
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.scss']
})
export class ProfileOverviewComponent {
  constructor(
    private userService: UserService,
    private router: Router
  ) {
  }

  get userDetails$() {
    return this.userService.userDetails$;
  }

  logoutUser() {
    if (this.userService.isLoggedIn()) {
      this.userService.logoutUser();
      this.router.navigate(['/']);
    }
  }
}
