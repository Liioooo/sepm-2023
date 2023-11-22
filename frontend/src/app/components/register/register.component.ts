import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';
import { ControlsOf } from '../../types/controls-of';
import { UpdateUserDetailDto } from '../../dtos/update-user-detail-dto';
import { UserLocationDto } from '../../dtos/user-location-dto';
import { matchValidator } from '../../validators/match-validator';
import { ErrorResponseDto } from '../../dtos/error-response-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup<ControlsOf<UpdateUserDetailDto>>;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService,
    private router: Router
  ) {
    this.registerForm = this.formBuilder.group<ControlsOf<UpdateUserDetailDto>>({
      firstName: this.formBuilder.control('', [Validators.required]),
      lastName: this.formBuilder.control('', [Validators.required]),
      email: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: this.formBuilder.control('', [Validators.required]),
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

  registerUser() {
    this.registerForm.markAllAsTouched();
    this.registerForm.updateValueAndValidity();
    if (this.registerForm.valid) {
      this.userService.registerUser(this.getRegisterUserData())
        .subscribe({
          next: () => {
            this.toastService.showSuccess('Success', 'Registered as new user');
            this.router.navigate(['/']);
          },
          error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
        });
    }
  }

  private getRegisterUserData(): UpdateUserDetailDto {
    let user: UpdateUserDetailDto = this.registerForm.value as UpdateUserDetailDto;

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
