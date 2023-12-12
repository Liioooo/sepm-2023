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
import { tap } from 'rxjs/operators';

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

  occupiedSeats: Map<String, boolean> = new Map<String, boolean>();

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
    this.event$ = this.eventService.getEvent(Number(this.route.snapshot.paramMap.get('id'))).pipe(
      tap(event => {
        for (let seat of event.occupiedSeats) {
          this.occupiedSeats.set(`${seat.rowNumber}:${seat.seatNumber}`, true);
        }
      })
    );

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
            .map(t => [`${t.seatNumber}:${t.rowNumber}`, true]));
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

  toggleSeat(rowNumber: number, seatNumber: number) {
    if (this.selectedSeats.get(`${rowNumber}:${seatNumber}`)) {
      this.selectedSeats.delete(`${rowNumber}:${seatNumber}`);
    } else {
      if (this.isSelectReserved && !this.selectedSeatsInReservation.has(`${rowNumber}:${seatNumber}`)) {
        return;
      }
      this.selectedSeats.set(`${rowNumber}:${seatNumber}`, true);
    }
  }

  isSeatSelected(rowNumber: number, seatNumber: number) {
    return this.selectedSeats.get(`${rowNumber}:${seatNumber}`) ?? false;
  }

  decrementStanding() {
    this.selectedStanding = Math.max(this.selectedStanding - 1, 0);
  }

  getRemainingStandings(event: EventDetailDto): number {
    return event.hall.standingCount - event.occupiedStandings;
  }

  incrementStanding(event: EventDetailDto) {
    if (this.isSelectReserved) {
      this.selectedStanding = Math.min(this.selectedStanding + 1, this.selectedStandingInReservation);
      return;
    }

    this.selectedStanding = Math.min(this.selectedStanding + 1, this.getRemainingStandings(event));
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

  isOccupied(rowNumber: number, seatNumber: number): boolean {
    return this.occupiedSeats.has(`${rowNumber}:${seatNumber}`);
  }

  getPopoverText(rowNumber: number, seatNumber: number): string {
    if (this.isOccupied(rowNumber, seatNumber)) {
      return 'Occupied';
    } else {
      return `Row ${rowNumber}, Seat ${seatNumber}`;
    }
  }
}
