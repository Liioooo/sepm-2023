import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserLoginDto } from '../../dtos/user-login-dto';
import { ControlsOf } from '../../types/controls-of';
import { ErrorResponseDto } from '../../dtos/error-response-dto';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginForm: FormGroup<ControlsOf<UserLoginDto>>;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
    this.loginForm = this.formBuilder.group<ControlsOf<UserLoginDto>>({
      email: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.required, Validators.minLength(8)])
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  loginUser() {
    this.loginForm.markAllAsTouched();
    if (this.loginForm.valid) {
      this.authenticateUser(this.loginForm.value as UserLoginDto);
    }
  }

  /**
   * Send authentication data to the userService. If the authentication was successfully, the user will be forwarded to the message page
   *
   * @param loginDto authentication data from the user login form
   */
  authenticateUser(loginDto: UserLoginDto) {
    console.log('Try to authenticate user: ' + loginDto.email);
    this.userService.loginUser(loginDto).subscribe({
      next: () => {
        console.log('Successfully logged in user: ' + loginDto.email);
        this.router.navigate(['/']);
      },
      error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
    });
  }

}
