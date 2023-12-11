import { Component } from '@angular/core';
import { EventDetailDto } from '../../../dtos/event-detail-dto';
import { EventService } from '../../../services/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TicketService } from '../../../services/ticket.service';
import { TicketSelectMode } from '../../../types/ticket-select-mode';
import { OrderService } from '../../../services/order.service';
import { OrderType } from '../../../types/order-type';
import { TicketCategory } from '../../../types/ticket-category';
import { ErrorResponseDto } from '../../../dtos/error-response-dto';
import { ToastService } from '../../../services/toast.service';
import { ErrorFormatterService } from '../../../services/error-formatter.service';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent {
  event$: Observable<EventDetailDto>;
  mode: TicketSelectMode;
  reservationId: number;
  selectedSeats: Map<String, boolean> = new Map<String, boolean>();
  selectedStanding: number = 0;

  selectedSeatsInReservation: Map<String, boolean> = new Map<String, boolean>();
  selectedStandingInReservation: number = 0;

  constructor(
    private eventService: EventService,
    private ticketService: TicketService,
    private orderService: OrderService,
    private router: Router,
    private route: ActivatedRoute,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
    this.event$ = this.eventService.getEvent(Number(this.route.snapshot.paramMap.get('id')));
    this.reservationId = Number(this.route.snapshot.paramMap.get('reservationId'));
    this.mode = this.route.snapshot.data.type ?? TicketSelectMode.SELECT_NEW;

    if (this.isSelectReserved) {
      this.orderService.getOrder(this.reservationId).subscribe({
        next: order => {
          if (order.orderType !== OrderType.RESERVE) {
            this.toastService.showError('Error', 'This order is not a reservation');
            this.router.navigate(['/profile']);
          }

          this.selectedSeats = new Map(order.tickets
            .filter(t => t.ticketCategory === TicketCategory.SEATING)
            .map(t => [`${t.seatNumber}:${t.tierNumber}`, true]));
          this.selectedSeatsInReservation = new Map(this.selectedSeats);

          this.selectedStanding = order.tickets
            .filter(t => t.ticketCategory === TicketCategory.STANDING)
            .length;
          this.selectedStandingInReservation = this.selectedStanding;
        },
        error: err => {
          this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
          this.router.navigate(['/profile']);
        }
      });
    }
  }

  artistName(event: EventDetailDto): string {
    return event.artist.fictionalName || (`${event.artist.firstname} ${event.artist.lastname}`);
  }

  get selectedSeatsLength(): number {
    return this.selectedSeats?.size ?? 0;
  }

  getTotalPrice(event: EventDetailDto): number {
    return this.selectedSeatsLength * event.seatPrice + this.selectedStanding * event.standingPrice;
  }

  toggleSeat(tierNumber: number, seatNumber: number) {
    if (this.selectedSeats.get(`${tierNumber}:${seatNumber}`)) {
      this.selectedSeats.delete(`${tierNumber}:${seatNumber}`);
    } else {
      if (this.isSelectReserved && !this.selectedSeatsInReservation.has(`${tierNumber}:${seatNumber}`)) {
        return;
      }
      this.selectedSeats.set(`${tierNumber}:${seatNumber}`, true);
    }
  }

  isSeatSelected(tierNumber: number, seatNumber: number) {
    return this.selectedSeats.get(`${tierNumber}:${seatNumber}`) ?? false;
  }

  decrementStanding() {
    this.selectedStanding = Math.max(this.selectedStanding - 1, 0);
  }

  incrementStanding(event: EventDetailDto) {
    if (this.isSelectReserved) {
      this.selectedStanding = Math.min(this.selectedStanding + 1, this.selectedStandingInReservation);
      return;
    }

    // TODO: max needs to respect already bought standing tickets
    this.selectedStanding = Math.min(this.selectedStanding + 1, event.hall.standingCount);
  }

  buyTickets() {
    this.ticketService.selectedSeats = this.selectedSeats;
    this.ticketService.selectedStanding = this.selectedStanding;
    if (this.isSelectNew) {
      this.router.navigate(['buy'], { relativeTo: this.route });
    } else {
      this.router.navigate(['confirm'], { relativeTo: this.route });
    }
  }

  reserveTickets() {
    this.ticketService.selectedSeats = this.selectedSeats;
    this.ticketService.selectedStanding = this.selectedStanding;
    this.router.navigate(['reserve'], { relativeTo: this.route });
  }

  get isSelectNew(): boolean {
    return this.mode === TicketSelectMode.SELECT_NEW;
  }

  get isSelectReserved(): boolean {
    return this.mode === TicketSelectMode.SELECT_RESERVED;
  }
}
