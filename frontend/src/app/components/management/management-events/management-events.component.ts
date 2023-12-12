import { Component } from '@angular/core';
import { EventSearchDto } from '../../../dtos/event-search-dto';
import { EventService } from '../../../services/event.service';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../../types/pageable-request';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { tap } from 'rxjs/operators';
import { EventDetailDto } from '../../../dtos/event-detail-dto';

@Component({
  selector: 'app-management-events',
  templateUrl: './management-events.component.html',
  styleUrls: ['./management-events.component.scss']
})
export class ManagementEventsComponent {
  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;
  public events$: Observable<EventDetailDto[]>;
  public searchAttributes$ = new BehaviorSubject<EventSearchDto>({});
  private onPageChange$ = new BehaviorSubject<number>(0);

  constructor(
    private service: EventService,
    private route: ActivatedRoute
  ) {
    this.events$ = combineLatest([this.searchAttributes$, this.searchTerm$, this.onPageChangeDistinct$]).pipe(
      debounceTime(250),
      switchMap(([searchAttributes, search, page]) => {
        return this.service.getEvents({
          ...searchAttributes
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
  }

  get searchTerm$(): Observable<string> {
    return this.route.queryParams.pipe(
      map(params => params.search)
    );
  }

  private get onPageChangeDistinct$(): Observable<number> {
    return this.onPageChange$.pipe(
      distinctUntilChanged()
    );
  }

  onPageChange(newPage: number) {
    this.onPageChange$.next(newPage - 1);
  }

}
