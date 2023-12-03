import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { ControlsOf } from '../../types/controls-of';
import { ErrorResponseDto } from '../../dtos/error-response-dto';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';
import { ResetPasswordDto } from '../../dtos/reset-password-dto';
import { matchValidator } from '../../validators/match-validator';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent {

  form: FormGroup<ControlsOf<Partial<ResetPasswordDto>>>;

  public isLoading = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
    this.form = this.formBuilder.group<ControlsOf<Partial<ResetPasswordDto>>>({
      newPassword: this.formBuilder.control('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: this.formBuilder.control('', [Validators.required])
    }, {
      validators: matchValidator('confirmPassword', 'newPassword')
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  submitForm() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.isLoading = true;
      this.resetPassword({
        ...this.form.value,
        token: this.router.parseUrl(this.router.url).queryParams['token']
      } as ResetPasswordDto);
    }
  }

  /**
   * Sends a request to reset the password
   *
   * @param resetPasswordDto data to reset the password
   */
  resetPassword(resetPasswordDto: ResetPasswordDto) {
    this.userService.resetPassword(resetPasswordDto).subscribe({
      next: () => {
        console.log('Successfully reset password');
        this.toastService.showSuccess('Success', 'Password has been set successfully, you can now login with your new password.');
        this.router.navigate(['/login'], { replaceUrl: true });
      },
      error: err => {
        this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
        this.isLoading = false;
      }
    });
  }
}
