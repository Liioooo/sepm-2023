import { Component, Input, OnInit } from '@angular/core';
import { EventDetailDto } from '../../../dtos/event-detail-dto';

@Component({
  selector: 'app-event-item',
  templateUrl: './event-item.component.html',
  styleUrls: ['./event-item.component.scss']
})
export class EventItemComponent implements OnInit {

  @Input() event!: EventDetailDto;

  constructor() {
  }

  ngOnInit() {

  }

  get price() {
    return Math.min(this.event.seatPrice, this.event.standingPrice);
  }

  get artist() {
    return this.event.artist.fictionalName || (`${this.event.artist.firstname} ${this.event.artist.lastname}`);
  }

}
