import { Component } from '@angular/core';
import { EventService } from '../../services/event.service';
import { EventDetailDto } from '../../dtos/event-detail-dto';
import { EventSearchDto } from '../../dtos/event-search-dto';
import { BehaviorSubject, combineLatest, debounceTime, map, Observable, of, switchMap } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { LocationDetailDto } from '../../dtos/location-detail-dto';
import { LocationsService } from '../../services/location.service';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent {

  public events$: Observable<EventDetailDto[]>;

  public location$: Observable<LocationDetailDto>;

  public searchAttributes$ = new BehaviorSubject<EventSearchDto>({});

  constructor(private eventService: EventService, private locationsService: LocationsService, private route: ActivatedRoute) {
    this.events$ = combineLatest([this.searchAttributes$, this.searchTerm$, this.locationId$]).pipe(
      debounceTime(250),
      switchMap(([searchAttributes, search, locationId]) => {
        return this.eventService.getEvents({
          ...searchAttributes,
          search,
          locationId
        });
      })
    );

    this.location$ = this.locationId$.pipe(
      switchMap(id => id ? this.locationsService.getLocationById(Number(id)) : of(null))
    );
  }

  get searchTerm$(): Observable<string> {
    return this.route.queryParams.pipe(
      map(params => params.search)
    );
  }

  get locationId$(): Observable<string> {
    return this.route.queryParams.pipe(
      map(params => params.locationId)
    );
  }

}
