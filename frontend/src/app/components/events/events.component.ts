import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { EventDetailDto } from '../../dtos/event-detail-dto';
import { EventSearchDto } from '../../dtos/event-search-dto';
import { debounceTime, Subject } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  public events: EventDetailDto[] = [];

  public searchAttributes$ = new Subject<EventSearchDto>();

  public attributes: EventSearchDto = {};
  public searchTerm: string;

  constructor(private eventService: EventService, private route: ActivatedRoute) {
    this.searchAttributes$.pipe(
      debounceTime(250)
    ).subscribe(attr => {
      this.attributes = attr;
      this.updateAttributes();
    });

    this.route.queryParams.pipe(
      debounceTime(250)
    ).subscribe(params => {
      if (params.search !== undefined) {
        this.searchTerm = params.search;
        this.updateAttributes();
      }
    });
  }

  ngOnInit() {
    this.searchAttributes$.next({});
  }

  updateAttributes() {
    this.eventService.getEvents({
      ...this.attributes,
      search: this.searchTerm
    }).subscribe(
      (events) => this.events = events
    );
  }

}
