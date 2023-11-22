import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  public isNavbarCollapsed: boolean = true;

  constructor(public userService: UserService, private router: Router) {
  }

  get isLoginRegister(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }

}
