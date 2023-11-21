import { Component } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UserLoginDto } from '../../dtos/user-login-dto';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginForm: UntypedFormGroup;
  error: string;

  constructor(private formBuilder: UntypedFormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  loginUser() {
    this.loginForm.markAllAsTouched();
    if (this.loginForm.valid) {
      const loginDto: UserLoginDto = {
        email: this.loginForm.controls.email.value,
        password: this.loginForm.controls.password.value
      };
      this.authenticateUser(loginDto);
    }
  }

  /**
   * Send authentication data to the authService. If the authentication was successfully, the user will be forwarded to the message page
   *
   * @param loginDto authentication data from the user login form
   */
  authenticateUser(loginDto: UserLoginDto) {
    console.log('Try to authenticate user: ' + loginDto.email);
    this.authService.loginUser(loginDto).subscribe({
      next: () => {
        console.log('Successfully logged in user: ' + loginDto.email);
        this.router.navigate(['/']);
      },
      error: error => {
        const parsed = JSON.parse(error.error);
        this.error = parsed.error;
      }
    });
  }

  resetPassword() {
    // TODO
  }

}
