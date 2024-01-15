import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ControlsOf } from '../../../../types/controls-of';
import { ToastService } from '../../../../services/toast.service';
import { ErrorFormatterService } from '../../../../services/error-formatter.service';
import { Router } from '@angular/router';
import { ErrorResponseDto } from '../../../../dtos/error-response-dto';
import { UserService } from '../../../../services/user.service';
import { UserLocationDto } from '../../../../dtos/user-location-dto';
import { matchValidator } from '../../../../validators/match-validator';
import { UserCreateDto } from '../../../../dtos/user-create-dto';
import { UserRole } from '../../../../enums/userRole';

@Component({
  selector: 'app-management-user-create',
  templateUrl: './management-user-create.component.html',
  styleUrls: ['./management-user-create.component.scss']
})
export class ManagementUserCreateComponent {
  form: FormGroup<ControlsOf<UserCreateDto>>;
  userRoles: string[] = Object.values(UserRole);

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService,
    private router: Router
  ) {
    this.form = this.formBuilder.group<ControlsOf<UserCreateDto>>({
      firstName: this.formBuilder.control('', [Validators.required]),
      lastName: this.formBuilder.control('', [Validators.required]),
      email: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: this.formBuilder.control('', [Validators.required]),
      isLocked: this.formBuilder.control<boolean>(false),
      role: this.formBuilder.control<UserRole>(UserRole.ROLE_USER, []),
      location: this.formBuilder.group<ControlsOf<UserLocationDto>>({
        address: this.formBuilder.control('', []),
        postalCode: this.formBuilder.control('', []),
        city: this.formBuilder.control('', []),
        country: this.formBuilder.control('', [])
      })
    }, {
      validators: matchValidator('confirmPassword', 'password')
    });
  }

  createUser() {
    this.form.markAllAsTouched();
    this.form.updateValueAndValidity();

    if (this.form.valid) {
      this.userService.createUser(this.getCreatedUserData())
        .subscribe({
          next: () => {
            this.toastService.showSuccess('Success', 'Created new user');
            this.router.navigate(['management/users']);
          },
          error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
        });
    }
  }

  private getCreatedUserData(): UserCreateDto {
    let user: UserCreateDto = this.form.value as UserCreateDto;

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
