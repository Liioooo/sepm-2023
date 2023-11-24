import { Component } from '@angular/core';
import { EventService } from '../../services/event.service';
import { EventDetailDto } from '../../dtos/event-detail-dto';
import { EventSearchDto } from '../../dtos/event-search-dto';
import { BehaviorSubject, debounceTime, map, mergeMap, Observable, switchMap } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent {

  public events$: Observable<EventDetailDto[]>;

  public searchAttributes$ = new BehaviorSubject<EventSearchDto>({});

  constructor(private eventService: EventService, private route: ActivatedRoute) {
    this.events$ = this.searchAttributes$.pipe(
      mergeMap(searchAttributes => this.searchTerm$.pipe(map(search => ({
        ...searchAttributes,
        search
      })))),
      debounceTime(250),
      switchMap(search => this.eventService.getEvents(search))
    );
  }

  get searchTerm$(): Observable<string> {
    return this.route.queryParams.pipe(
      map(params => params.search)
    );
  }

}
