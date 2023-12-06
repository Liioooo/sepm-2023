import { Component } from '@angular/core';
import { EventDetailDto } from '../../../dtos/event-detail-dto';
import { EventService } from '../../../services/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TicketService } from '../../../services/ticket.service';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent {
  event$: Observable<EventDetailDto>;
  selectedSeats: Map<String, boolean> = new Map<String, boolean>();
  selectedStanding: number = 0;

  constructor(
    private eventService: EventService,
    private ticketService: TicketService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.event$ = this.eventService.getEvent(Number(this.route.snapshot.paramMap.get('id')));
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
    // TODO: max needs to respect already bought standing tickets
    this.selectedStanding = Math.min(this.selectedStanding + 1, event.hall.standingCount);
  }

  buyTickets() {
    this.ticketService.selectedSeats = this.selectedSeats;
    this.ticketService.selectedStanding = this.selectedStanding;
    this.router.navigate(['buy'], { relativeTo: this.route });
  }

  reserveTickets() {
    this.ticketService.selectedSeats = this.selectedSeats;
    this.ticketService.selectedStanding = this.selectedStanding;
    this.router.navigate(['reserve'], { relativeTo: this.route });
  }
}
