import { Component, OnInit } from '@angular/core';
import { EventDetailDto } from '../../../dtos/event-detail-dto';
import { EventService } from '../../../services/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom, Observable } from 'rxjs';
import { TicketService } from '../../../services/ticket.service';
import { TicketSelectMode } from '../../../types/ticket-select-mode';
import { OrderService } from '../../../services/order.service';
import { TicketCategory } from '../../../types/ticket-category';
import { ErrorResponseDto } from '../../../dtos/error-response-dto';
import { ToastService } from '../../../services/toast.service';
import { ErrorFormatterService } from '../../../services/error-formatter.service';
import { tap } from 'rxjs/operators';
import { OrderDetailDto } from '../../../dtos/order-detail-dto';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent implements OnInit {
  event$: Observable<EventDetailDto>;
  mode: TicketSelectMode;
  orderId: number;
  selectedSeats: Map<String, boolean> = new Map<String, boolean>();
  selectedStanding: number = 0;

  occupiedSeats: Map<String, boolean> = new Map<String, boolean>();

  selectedSeatsInOrder: Map<String, boolean> = new Map<String, boolean>();
  selectedStandingInOrder: number = 0;

  order: void | OrderDetailDto;

  constructor(
    private eventService: EventService,
    private ticketService: TicketService,
    private orderService: OrderService,
    private router: Router,
    private route: ActivatedRoute,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
  }

  async ngOnInit(): Promise<void> {
    let eventId = Number(this.route.snapshot.paramMap.get('id'));

    this.orderId = Number(this.route.snapshot.paramMap.get('orderId'));
    this.mode = this.route.snapshot.data.type ?? TicketSelectMode.SELECT_NEW;

    if (this.isUpdate || this.isSelectReserved) {
      this.order = await firstValueFrom(this.orderService.getOrder(this.orderId)).catch(
        err => {
          this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
          this.router.navigate(['/profile']);
        }
      );

      if (this.order) {
        this.selectedSeats = new Map(this.order.tickets
          .filter(t => t.ticketCategory === TicketCategory.SEATING)
          .map(t => [`${t.rowNumber}:${t.seatNumber}`, true]));
        this.selectedSeatsInOrder = new Map(this.selectedSeats);

        this.selectedStanding = this.order.tickets
          .filter(t => t.ticketCategory === TicketCategory.STANDING)
          .length;
        this.selectedStandingInOrder = this.selectedStanding;
        eventId = this.order.event.id;
      }
    }

    this.event$ = this.eventService.getEvent(eventId).pipe(
      tap(event => {
        for (let seat of event.occupiedSeats) {
          this.occupiedSeats.set(`${seat.rowNumber}:${seat.seatNumber}`, true);
        }
      })
    );
  }

  get actionTitle() {
    return this.isUpdate ? 'Update Tickets' : 'Select Tickets';
  }

  get actionButtonText() {
    return this.isUpdate ? 'Update Tickets' : 'Buy Tickets';
  }

  artistName(event: EventDetailDto): string {
    return event.artist.fictionalName || (`${event.artist.firstname} ${event.artist.lastname}`);
  }

  get selectedSeatsLength(): number {
    return this.selectedSeats?.size ?? 0;
  }

  getTotalPrice(event: EventDetailDto): number {
    const totalForSelected = this.selectedSeatsLength * event.seatPrice + this.selectedStanding * event.standingPrice;

    if (this.isUpdate) {
      return -(this.selectedSeatsInOrder.size * event.seatPrice + this.selectedStandingInOrder * event.standingPrice - totalForSelected);
    }

    return totalForSelected;
  }

  toggleSeat(rowNumber: number, seatNumber: number) {
    if (this.selectedSeats.get(`${rowNumber}:${seatNumber}`)) {
      this.selectedSeats.delete(`${rowNumber}:${seatNumber}`);
    } else {
      if (this.isSelectReserved && !this.selectedSeatsInOrder.has(`${rowNumber}:${seatNumber}`)) {
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
    if (this.isSelectReserved || this.isUpdate) {
      return this.selectedStandingInOrder;
    }

    return event.hall.standingCount - event.occupiedStandings;
  }

  incrementStanding(event: EventDetailDto) {
    if (this.isSelectReserved || this.isUpdate) {
      this.selectedStanding = Math.min(this.selectedStanding + 1, this.selectedStandingInOrder);
      return;
    }

    this.selectedStanding = Math.min(this.selectedStanding + 1, this.getRemainingStandings(event));
  }

  buyTickets() {
    this.ticketService.selectedSeats = this.selectedSeats;
    this.ticketService.selectedStanding = this.selectedStanding;
    if (this.isUpdate) {
      this.saveTicketIdsToTicketService();
    }

    if (this.isSelectNew) {
      this.router.navigate(['buy'], { relativeTo: this.route });
    } else {
      this.router.navigate(['confirm'], { relativeTo: this.route });
    }
  }

  saveTicketIdsToTicketService(): void {
    if (!this.order) {
      return;
    }
    let standingTickets = this.selectedStanding;
    this.ticketService.selectedTickets = this.order.tickets.filter(ticket => {
      if (ticket.ticketCategory === TicketCategory.SEATING) {
        return this.selectedSeats.has(`${ticket.rowNumber}:${ticket.seatNumber}`);
      } else if (ticket.ticketCategory === TicketCategory.STANDING) {
        return standingTickets-- > 0;
      }
    }).map(ticket => ticket.id);
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

  get isUpdate(): boolean {
    return this.mode === TicketSelectMode.UPDATE;
  }

  isOccupied(rowNumber: number, seatNumber: number): boolean {
    if (this.isSelectReserved || this.isUpdate) {
      return !this.selectedSeatsInOrder.has(`${rowNumber}:${seatNumber}`);
    } else {
      return this.occupiedSeats.has(`${rowNumber}:${seatNumber}`);
    }
  }

  getPopoverText(rowNumber: number, seatNumber: number): string {
    if (this.isOccupied(rowNumber, seatNumber)) {
      if (this.isSelectReserved) {
        return 'Not reserved';
      }
      if (this.isUpdate) {
        return 'Not included in Order';
      }
      return 'Occupied';
    } else {
      return `Row ${rowNumber}, Seat ${seatNumber}`;
    }
  }
}
