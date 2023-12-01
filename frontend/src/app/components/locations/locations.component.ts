import { Component } from '@angular/core';
import { LocationsService } from '../../services/location.service';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { LocationDetailDto } from '../../dtos/location-detail-dto';
import { LocationSearchDto } from '../../dtos/location-search-dto';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../types/pageable-request';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.scss']
})
export class LocationsComponent {

  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;

  public locations$: Observable<LocationDetailDto[]>;

  public searchAttributes$ = new BehaviorSubject<LocationSearchDto>({});

  private onPageChange$ = new BehaviorSubject<number>(0);

  constructor(private locationsService: LocationsService) {
    this.locations$ = combineLatest([this.searchAttributes$, this.onPageChangeDistinct$]).pipe(
      debounceTime(250),
      switchMap(([search, page]) => {
        return this.locationsService.getLocations(search, { page, size: 20 });
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

  onPageChange(newPage: number) {
    this.onPageChange$.next(newPage - 1);
  }

  private get onPageChangeDistinct$(): Observable<number> {
    return this.onPageChange$.pipe(
      distinctUntilChanged()
    );
  }

}
