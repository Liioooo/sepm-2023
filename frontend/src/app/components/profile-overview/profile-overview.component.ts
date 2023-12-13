import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../../services/order.service';
import { OrderListDto } from '../../dtos/order-list-dto';
import { Observable } from 'rxjs';
import { UserService } from '../../services/user.service';
import { ErrorResponseDto } from '../../dtos/error-response-dto';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';

@Component({
  selector: 'app-profile-overview',
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.scss']
})
export class ProfileOverviewComponent implements OnInit {
  public $orders: Observable<OrderListDto[]>;

  constructor(
    private orderService: OrderService,
    private userService: UserService,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.$orders = this.orderService.getOwnOrders();
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

  deleteReservation(order: OrderListDto) {
    this.orderService.deleteReservation(order.id).subscribe({
      next: () => {
        this.toastService.showSuccess('Success', 'Reservation deleted successfully.');
        this.$orders = this.orderService.getOwnOrders();
      },
      error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
    });
  }
}
