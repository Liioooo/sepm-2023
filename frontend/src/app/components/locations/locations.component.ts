import { Component } from '@angular/core';
import { LocationsService } from '../../services/location.service';
import { BehaviorSubject, debounceTime, Observable, switchMap } from 'rxjs';
import { LocationDetailDto } from '../../dtos/location-detail-dto';
import { LocationSearchDto } from '../../dtos/location-search-dto';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.scss']
})
export class LocationsComponent {

  public locations$: Observable<LocationDetailDto[]>;

  public searchAttributes$ = new BehaviorSubject<LocationSearchDto>({});

  constructor(private locationsService: LocationsService) {
    this.locations$ = this.searchAttributes$.pipe(
      debounceTime(250),
      switchMap(search => this.locationsService.getLocations(search))
    );
  }

}
