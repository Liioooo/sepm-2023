import { Component, EventEmitter, Input, Output } from '@angular/core';
import { OrderListDto } from '../../../dtos/order-list-dto';
import { OrderType } from '../../../types/order-type';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.scss']
})
export class OrderItemComponent {

  @Input() order!: OrderListDto;

  @Output() deleteReservationEvent = new EventEmitter<OrderListDto>();

  get event() {
    return this.order.event;
  }

  get artist() {
    return this.event.artist.fictionalName || (`${this.event.artist.firstname} ${this.event.artist.lastname}`);
  }

  get isReservation() {
    return this.order.orderType === OrderType.RESERVE;
  }

  get isExpired() {
    return this.order.orderType === OrderType.RESERVE && (this.order.event.startDate.getTime() - Date.now()) < 1000 * 60 * 30;
  }

  deleteReservation() {
    this.deleteReservationEvent.emit(this.order);
  }

}
