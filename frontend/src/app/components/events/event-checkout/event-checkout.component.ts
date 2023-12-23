import { Component, OnInit } from '@angular/core';
import { EventDetailDto } from '../../../dtos/event-detail-dto';
import { EventService } from '../../../services/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom, Observable } from 'rxjs';
import { TicketService } from '../../../services/ticket.service';
import { OrderType } from '../../../types/order-type';
import { OrderCreateDto } from '../../../dtos/order-create-dto';
import { TicketCategory } from '../../../types/ticket-category';
import { TicketCreateDto } from '../../../dtos/ticket-create-dto';
import { OrderService } from '../../../services/order.service';
import { ToastService } from '../../../services/toast.service';
import { ErrorResponseDto } from '../../../dtos/error-response-dto';
import { ErrorFormatterService } from '../../../services/error-formatter.service';
import { CheckoutMode } from '../../../types/checkout-mode';
import { RedeemReservationDto } from '../../../dtos/redeem-reservation-dto';
import { OrderUpdateTicketsDto } from '../../../dtos/order-update-tickets-dto';
import { TicketOrderUpdateDto } from '../../../dtos/ticket-order-update-dto';
import { OrderDetailDto } from '../../../dtos/order-detail-dto';

@Component({
  selector: 'app-event-checkout',
  templateUrl: './event-checkout.component.html',
  styleUrls: ['./event-checkout.component.scss']
})
export class EventCheckoutComponent implements OnInit {
  event$: Observable<EventDetailDto>;
  mode: CheckoutMode;
  orderId: number;
  order?: OrderDetailDto;

  constructor(
    private eventService: EventService,
    private ticketService: TicketService,
    private orderService: OrderService,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  async ngOnInit(): Promise<void> {
    this.orderId = Number(this.route.snapshot.paramMap.get('orderId'));
    this.mode = this.route.snapshot.data.type ?? CheckoutMode.BUY;
    let eventId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.isUpdate) {
      let order = await firstValueFrom(this.orderService.getOrder(this.orderId)).catch(
        err => {
          this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
          this.router.navigate(['/profile']);
        }
      );

      if (order) {
        this.order = order;
        eventId = order.event.id;
      }
    }

    this.event$ = this.eventService.getEvent(eventId);
  }

  get isBuy(): boolean {
    return this.mode === CheckoutMode.BUY;
  }

  get isReserve(): boolean {
    return this.mode === CheckoutMode.RESERVE;
  }

  get isBuyingReservation(): boolean {
    return this.mode === CheckoutMode.BUY_RESERVATION;
  }

  get isUpdate(): boolean {
    return this.mode === CheckoutMode.UPDATE;
  }

  get isOrderReservation(): boolean {
    return this.order?.orderType === OrderType.RESERVE;
  }

  get selectedSeats() {
    if (this.isUpdate) {
      return this.order.tickets.filter(t => t.ticketCategory == TicketCategory.SEATING).length - this.ticketService.selectedSeats.size;
    }
    return this.ticketService.selectedSeats.size;
  }

  get selectedStanding() {
    if (this.isUpdate) {
      return this.order.tickets.filter(t => t.ticketCategory == TicketCategory.STANDING).length - this.ticketService.selectedStanding;
    }
    return this.ticketService.selectedStanding;
  }

  getStandingPrice(event: EventDetailDto) {
    const standingPrice = this.selectedStanding * event.standingPrice;
    if (this.isUpdate) {
      return -standingPrice;
    }
    return standingPrice;
  }

  getSeatingPrice(event: EventDetailDto) {
    const seatingPrice = this.selectedSeats * event.seatPrice;
    if (this.isUpdate) {
      return -seatingPrice;
    }
    return seatingPrice;
  }

  artistName(event: EventDetailDto): string {
    return event.artist.fictionalName || (`${event.artist.firstname} ${event.artist.lastname}`);
  }

  getTotalPrice(event: EventDetailDto): number {
    return this.getSeatingPrice(event) + this.getStandingPrice(event);
  }

  submitNewOrder(event: EventDetailDto) {
    const orderType = this.mode === CheckoutMode.BUY ? OrderType.BUY : OrderType.RESERVE;

    const order: OrderCreateDto = {
      tickets: this.generateTickets(),
      orderType,
      eventId: event.id
    };

    this.orderService.createOrder(order).subscribe({
      next: () => {
        this.toastService.showSuccess('Success', 'Order created successfully');
        this.router.navigate(['/profile']);
      },
      error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
    });
  }

  submitReservationPurchase() {
    const redeemReservationDto: RedeemReservationDto = {
      tickets: this.generateTickets()
    };

    this.orderService.purchaseReservation(this.orderId, redeemReservationDto).subscribe({
      next: () => {
        this.toastService.showSuccess('Success', 'Reservation redeemed successfully');
        this.router.navigate(['/profile']);
      },
      error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
    });
  }

  private generateTickets(): TicketCreateDto[] {
    let seats: TicketCreateDto[] = [];
    for (const [seat] of this.ticketService.selectedSeats) {
      const [rowNumber, seatNumber] = seat.split(':').map(Number);

      seats.push({
        ticketCategory: TicketCategory.SEATING,
        seatNumber,
        rowNumber
      });
    }

    return [
      ...seats,
      ...new Array<TicketCreateDto>(this.ticketService.selectedStanding).fill({
        ticketCategory: TicketCategory.STANDING
      })
    ];
  }

  submitUpdateOrderTickets() {
    const orderUpdateTicketsDto: OrderUpdateTicketsDto = {
      tickets: this.ticketService.selectedTickets.map(id => <TicketOrderUpdateDto>{
        id: id
      })
    };

    this.orderService.updateOrder(this.orderId, orderUpdateTicketsDto).subscribe({
      next: () => {
        this.toastService.showSuccess('Success', 'Order updated successfully');
        this.router.navigate(['/profile']);
      },
      error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
    });
  }
}
