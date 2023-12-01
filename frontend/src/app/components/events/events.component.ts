import { Component } from '@angular/core';
import { EventService } from '../../services/event.service';
import { EventDetailDto } from '../../dtos/event-detail-dto';
import { EventSearchDto } from '../../dtos/event-search-dto';
import {
  BehaviorSubject,
  combineLatest,
  debounceTime,
  distinctUntilChanged,
  map,
  Observable,
  of,
  switchMap
} from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { LocationDetailDto } from '../../dtos/location-detail-dto';
import { LocationsService } from '../../services/location.service';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../types/pageable-request';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent {

  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;

  public events$: Observable<EventDetailDto[]>;

  public location$: Observable<LocationDetailDto>;

  public searchAttributes$ = new BehaviorSubject<EventSearchDto>({});

  private onPageChange$ = new BehaviorSubject<number>(0);

  constructor(private eventService: EventService, private locationsService: LocationsService, private route: ActivatedRoute) {
    this.events$ = combineLatest([this.searchAttributes$, this.searchTerm$, this.locationId$, this.onPageChangeDistinct$]).pipe(
      debounceTime(250),
      switchMap(([searchAttributes, search, locationId, page]) => {
        return this.eventService.getEvents({
          ...searchAttributes,
          search,
          locationId
        }, { page, size: 20 });
      }),
      tap(page => {
        this.pageableState = {
          pageSize: 20,
          currentPage: page.currentPage + 1,
          totalPages: page.totalPages,
          totalElements: page.totalElements
        };
      }),
      map(page => page.content)
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

  onPageChange(newPage: number) {
    this.onPageChange$.next(newPage - 1);
  }

  private get onPageChangeDistinct$(): Observable<number> {
    return this.onPageChange$.pipe(
      distinctUntilChanged()
    );
  }

}
