import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  public isNavbarCollapsed: boolean = true;

  public searchForm: FormGroup = new FormGroup({
    search: new FormControl()
  });

  constructor(
    public userService: UserService,
    private router: Router
  ) {
  }

  get isLoginRegister(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }

  get userDetails$() {
    return this.userService.userDetails$;
  }

  async search() {
    if (this.searchForm.invalid) {
      return;
    }

    await this.router.navigate(['/events'], { queryParams: { search: this.searchForm.value.search } });
  }

}
