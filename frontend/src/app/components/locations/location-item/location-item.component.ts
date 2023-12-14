import { Component, Input } from '@angular/core';
import { LocationDetailDto } from '../../../dtos/location-detail-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-location-item',
  templateUrl: './location-item.component.html',
  styleUrls: ['./location-item.component.scss']
})
export class LocationItemComponent {

  constructor(private router: Router) {
  }

  @Input() location!: LocationDetailDto;

  public showEvents() {
    this.router.navigate(['/events'], { queryParams: { locationId: this.location.id } });
  }

}
