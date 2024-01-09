import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';
import { LocationsService } from '../../../../services/location.service';
import { Observable } from 'rxjs';
import { LocationHallsDto } from '../../../../dtos/location-halls-dto';

@Component({
  selector: 'app-management-location-details',
  templateUrl: './management-location-details.component.html',
  styleUrls: ['./management-location-details.component.scss']
})
export class ManagementLocationDetailsComponent {
  public location$: Observable<LocationHallsDto>;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService,
    private locationsService: LocationsService
  ) {
    this.route.paramMap.subscribe({
      next: paramMap => {
        const id = Number(paramMap.get('id'));
        if (Number.isNaN(id)) {
          this.toastService.showError('Error', 'Could not get details for this location');
          this.router.navigate(['/management/locations']);
        } else {
          this.location$ = this.locationsService.getLocationWithHallsById(id);
        }
      },
      error: err => {
        this.toastService.showError('Error', 'Could not get details for this location');
        this.router.navigate(['/management/locations']);
      }
    });
  }
}
