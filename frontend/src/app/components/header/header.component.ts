import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  public isNavbarCollapsed: boolean = true;

  constructor(public userService: UserService) {
  }

  ngOnInit() {
  }

}
