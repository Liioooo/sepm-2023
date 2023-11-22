import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { UpdateUserDetailDto } from '../../dtos/update-user-detail-dto';
import { UserLocationDto } from '../../dtos/user-location-dto';
import { filter, take } from 'rxjs';
import { ControlsOf } from '../../types/controls-of';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';
import { ErrorResponseDto } from '../../dtos/error-response-dto';
import { matchValidator } from '../../validators/match-validator';

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.component.html',
  styleUrls: ['./profile-settings.component.scss']
})
export class ProfileSettingsComponent {
  editAccountForm: FormGroup<ControlsOf<UpdateUserDetailDto>>;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
    this.editAccountForm = this.formBuilder.group<ControlsOf<UpdateUserDetailDto>>({
      firstName: this.formBuilder.control('', [Validators.required]),
      lastName: this.formBuilder.control('', [Validators.required]),
      email: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.minLength(8)]),
      confirmPassword: this.formBuilder.control('', []),
      location: this.formBuilder.group<ControlsOf<UserLocationDto>>({
        address: this.formBuilder.control('', []),
        postalCode: this.formBuilder.control('', []),
        city: this.formBuilder.control('', []),
        country: this.formBuilder.control('', [])
      })
    }, {
      validators: matchValidator('confirmPassword', 'password')
    });

    this.userService.userDetails$.pipe(filter(user => !!user), take(1)).subscribe(user => {
      this.editAccountForm.patchValue(user);
    });
  }

  updateProfile() {
    this.editAccountForm.markAllAsTouched();
    this.editAccountForm.updateValueAndValidity();
    if (this.editAccountForm.valid) {
      this.userService.updateMyUser(this.getUpdateUserDetailDto())
        .subscribe({
          next: value => this.toastService.showSuccess('Success', 'Updated profile data'),
          error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
        });
    }
  }

  private getUpdateUserDetailDto(): UpdateUserDetailDto {
    let user: UpdateUserDetailDto = this.editAccountForm.value as UpdateUserDetailDto;

    if (!user.password) {
      user.password = null;
    }

    if (!user.confirmPassword) {
      user.confirmPassword = null;
    }

    if (
      !user.location.city
      && !user.location.postalCode
      && !user.location.country
      && !user.location.address
    ) {
      user.location = null;
    }

    return user;
  }
}
