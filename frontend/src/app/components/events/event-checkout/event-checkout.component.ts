import { Component, OnInit } from '@angular/core';
import { EventDetailDto } from '../../../dtos/event-detail-dto';
import { EventService } from '../../../services/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
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

@Component({
  selector: 'app-event-checkout',
  templateUrl: './event-checkout.component.html',
  styleUrls: ['./event-checkout.component.scss']
})
export class EventCheckoutComponent implements OnInit {
  event$: Observable<EventDetailDto>;
  mode: CheckoutMode;
  reservationId: number;

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

  ngOnInit(): void {
    this.event$ = this.eventService.getEvent(Number(this.route.snapshot.paramMap.get('id')));
    this.reservationId = Number(this.route.snapshot.paramMap.get('reservationId'));
    this.mode = this.route.snapshot.data.type ?? CheckoutMode.BUY;
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

  get selectedSeats() {
    return this.ticketService.selectedSeats.size;
  }

  get selectedStanding() {
    return this.ticketService.selectedStanding;
  }

  getStandingPrice(event: EventDetailDto) {
    return this.selectedStanding * event.standingPrice;
  }

  getSeatingPrice(event: EventDetailDto) {
    return this.selectedSeats * event.seatPrice;
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

    this.orderService.purchaseReservation(this.reservationId, redeemReservationDto).subscribe({
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
      const [tierNumber, seatNumber] = seat.split(':').map(Number);

      seats.push({
        ticketCategory: TicketCategory.SEATING,
        seatNumber,
        tierNumber
      });
    }

    return [
      ...seats,
      ...new Array<TicketCreateDto>(this.ticketService.selectedStanding).fill({
        ticketCategory: TicketCategory.STANDING
      })
    ];
  }
}
