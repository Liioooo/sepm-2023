import { Component } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HallCreateDto } from '../../../../../dtos/hall-create-dto';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../../services/toast.service';
import { RowListDto } from '../../../../../dtos/row-list-dto';
import { HallService } from '../../../../../services/hall.service';
import { ErrorResponseDto } from '../../../../../dtos/error-response-dto';
import { ErrorFormatterService } from '../../../../../services/error-formatter.service';

interface CreateHallFormData {
  name: FormControl<string>;
  standingCount: FormControl<number>;
  rows: FormArray<FormControl<number>>;
}

@Component({
  selector: 'app-management-location-hall-create',
  templateUrl: './management-location-hall-create.component.html',
  styleUrls: ['./management-location-hall-create.component.scss']
})
export class ManagementLocationHallCreateComponent {
  createHallForm: FormGroup<CreateHallFormData>;
  locationId: number;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private toastService: ToastService,
    private router: Router,
    private hallService: HallService,
    private errorFormatterService: ErrorFormatterService
  ) {
    this.createHallForm = this.formBuilder.group<CreateHallFormData>({
      name: this.formBuilder.control('', [Validators.required]),
      standingCount: this.formBuilder.control(0, [Validators.required, Validators.min(0)]),
      rows: this.formBuilder.array<FormControl<number>>([])
    });
    this.route.paramMap.subscribe({
      next: paramMap => {
        const id = Number(paramMap.get('id'));
        if (Number.isNaN(id)) {
          this.toastService.showError('Error', 'Could not get the location of this hall');
          this.router.navigate(['/management/locations']);
        } else {
          this.locationId = id;
        }
      },
      error: err => {
        this.toastService.showError('Error', 'Could not get the location of this hall');
        this.router.navigate(['/management/locations']);
      }
    });
  }

  get rows(): FormArray {
    return this.createHallForm.get('rows') as FormArray;
  }

  createHall(): void {
    this.createHallForm.markAllAsTouched();
    if (this.createHallForm.valid) {
      this.hallService.createHall(this.hallCreateDtoFromFormData()).subscribe({
        next: () => {
          this.toastService.showSuccess('Success', 'Hall created successfully');
          this.router.navigate(['/management/locations', this.locationId, 'details']);
        },
        error: err => {
          this.toastService.showError(
            'Error',
            this.errorFormatterService.format(err['error'] as ErrorResponseDto)
          );
        }
      });
    }
  }

  addRow(): void {
    this.rows.push(this.formBuilder.control(1, [Validators.required, Validators.min(1)]));
  }

  addSeat(row: AbstractControl): void {
    row.setValue(row.value + 1);
  }

  removeSeat(row: AbstractControl): void {
    row.setValue(row.value - 1);
    if (row.value === 0) {
      this.rows.removeAt(this.rows.controls.indexOf(row));
    }
  }

  getTotalSeats() {
    return this.rows.controls.reduce((sum: number, row: FormControl<number>) => sum + row.value, 0);
  }

  private hallCreateDtoFromFormData(): HallCreateDto {
    const rowListDto: RowListDto[] = [];
    for (let i = 0; i < this.rows.controls.length; i++) {
      const rowFormControl = this.rows.controls[i];
      rowListDto.push({
        number: i + 1,
        numberOfSeats: rowFormControl.value
      });
    }

    return {
      name: this.createHallForm.get('name').value,
      standingCount: this.createHallForm.get('standingCount').value,
      rows: rowListDto,
      locationId: this.locationId
    };
  }
}
