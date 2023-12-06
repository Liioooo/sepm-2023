import { Component, OnInit } from '@angular/core';
import { EventDetailDto } from '../../../dtos/event-detail-dto';
import { EventService } from '../../../services/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TicketService } from '../../../services/ticket.service';
import { CheckoutType } from '../../../types/checkout-type';

@Component({
  selector: 'app-event-checkout',
  templateUrl: './event-checkout.component.html',
  styleUrls: ['./event-checkout.component.scss']
})
export class EventCheckoutComponent implements OnInit {
  event$: Observable<EventDetailDto>;
  type: CheckoutType;

  constructor(
    private eventService: EventService,
    private ticketService: TicketService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.event$ = this.eventService.getEvent(Number(this.route.snapshot.paramMap.get('id')));
    this.type = this.route.snapshot.data.type ?? CheckoutType.BUY;
  }

  get isBuy(): boolean {
    return this.type === CheckoutType.BUY;
  }

  get isReserve(): boolean {
    return this.type === CheckoutType.RESERVE;
  }

  get selectedSeats() {
    return this.ticketService.selectedSeats.size;
  }

  get selectedStanding() {
    return this.ticketService.selectedStanding;
  }

  getStandingPrice(event: EventDetailDto) {
    return this.selectedStanding * event.standingPrice
  }

  getSeatingPrice(event: EventDetailDto) {
    return this.selectedSeats * event.seatPrice
  }

  artistName(event: EventDetailDto): string {
    return event.artist.fictionalName || (`${event.artist.firstname} ${event.artist.lastname}`);
  }

  getTotalPrice(event: EventDetailDto): number {
    return this.getSeatingPrice(event) + this.getStandingPrice(event);
  }
}
