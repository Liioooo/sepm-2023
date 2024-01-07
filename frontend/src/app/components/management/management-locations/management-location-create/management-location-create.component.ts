import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ControlsOf } from '../../../../types/controls-of';
import { ToastService } from '../../../../services/toast.service';
import { ErrorFormatterService } from '../../../../services/error-formatter.service';
import { Router } from '@angular/router';
import { LocationCreateDto } from '../../../../dtos/location-create-dto';
import { ErrorResponseDto } from '../../../../dtos/error-response-dto';
import { LocationsService } from '../../../../services/location.service';

@Component({
  selector: 'app-management-location-create',
  templateUrl: './management-location-create.component.html',
  styleUrls: ['./management-location-create.component.scss']
})
export class ManagementLocationCreateComponent {
  createLocationForm: FormGroup<ControlsOf<LocationCreateDto>>;

  constructor(
    private formBuilder: FormBuilder,
    private toastService: ToastService,
    private locationsService: LocationsService,
    private errorFormatterService: ErrorFormatterService,
    private router: Router
  ) {
    this.createLocationForm = this.formBuilder.group<ControlsOf<LocationCreateDto>>({
      title: this.formBuilder.control('', [Validators.required]),
      address: this.formBuilder.control('', [Validators.required]),
      city: this.formBuilder.control('', [Validators.required]),
      country: this.formBuilder.control('', [Validators.required]),
      postalCode: this.formBuilder.control('', [Validators.required])
    });
  }

  createLocation(): void {
    this.createLocationForm.markAllAsTouched();
    this.createLocationForm.updateValueAndValidity();
    if (!this.createLocationForm.valid) {
      return;
    }

    this.locationsService.createLocation(this.getLocationCreateDto()).subscribe({
      next: () => {
        this.toastService.showSuccess('Success', 'Location created successfully');
        this.router.navigate(['/management/locations']);
      },
      error: err => {
        this.toastService.showError(
          'Error',
          this.errorFormatterService.format(err['error'] as ErrorResponseDto)
        );
      }
    });
  }

  private getLocationCreateDto(): LocationCreateDto {
    let location: LocationCreateDto = this.createLocationForm.value as LocationCreateDto;
    return location;
  }
}
