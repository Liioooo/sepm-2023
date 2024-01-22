import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../../services/order.service';
import { OrderListDto } from '../../dtos/order-list-dto';
import { map, Observable } from 'rxjs';
import { UserService } from '../../services/user.service';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';

@Component({
  selector: 'app-profile-overview',
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.scss']
})
export class ProfileOverviewComponent implements OnInit {
  public $upcomingOrders: Observable<OrderListDto[]>;
  public $pastOrders: Observable<OrderListDto[]>;
  public activeTab = 1;

  constructor(
    private orderService: OrderService,
    private userService: UserService,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService,
    private router: Router
  ) {
  }

  ngOnInit() {
    const $orders = this.orderService.getOwnOrders();

    this.$upcomingOrders = $orders.pipe(map(orders => {
      return orders.filter(o => {
        return o.event.startDate.getTime() > Date.now();
      });
    }));
    this.$pastOrders = $orders.pipe(map(orders => {
      return orders.filter(o => {
        return o.event.startDate.getTime() <= Date.now();
      });
    }));
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
