import { Component } from '@angular/core';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../../types/pageable-request';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { tap } from 'rxjs/operators';
import { LocationDetailDto } from '../../../dtos/location-detail-dto';
import { LocationsService } from '../../../services/location.service';
import { LocationSearchDto } from '../../../dtos/location-search-dto';

@Component({
  selector: 'app-management-locations',
  templateUrl: './management-locations.component.html',
  styleUrls: ['./management-locations.component.scss']
})
export class ManagementLocationsComponent {
  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;
  public locations$: Observable<LocationDetailDto[]>;
  public searchAttributes$ = new BehaviorSubject<LocationSearchDto>({});
  private onPageChange$ = new BehaviorSubject<number>(0);

  constructor(
    private locationService: LocationsService
  ) {
    this.locations$ = combineLatest([this.searchAttributes$, this.onPageChangeDistinct$]).pipe(
      debounceTime(250),
      switchMap(([searchAttributes, page]) => {
        return this.locationService.getLocations({
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

  private get onPageChangeDistinct$(): Observable<number> {
    return this.onPageChange$.pipe(
      distinctUntilChanged()
    );
  }

  onPageChange(newPage: number) {
    this.onPageChange$.next(newPage - 1);
  }
}
