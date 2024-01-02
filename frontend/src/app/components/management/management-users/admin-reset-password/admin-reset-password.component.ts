import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmailResetDto } from '../../../../dtos/email-reset-dto';
import { ControlsOf } from '../../../../types/controls-of';
import { UserService } from '../../../../services/user.service';
import { Router } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';
import { ErrorFormatterService } from '../../../../services/error-formatter.service';
import { ErrorResponseDto } from '../../../../dtos/error-response-dto';


@Component({
  selector: 'app-admin-reset-password',
  templateUrl: './admin-reset-password.component.html',
  styleUrls: ['./admin-reset-password.component.scss']
})
export class AdminResetPasswordComponent {

  form: FormGroup<ControlsOf<EmailResetDto>>;

  public isLoading = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
    this.form = this.formBuilder.group<ControlsOf<EmailResetDto>>({
      email: this.formBuilder.control('', [Validators.required, Validators.email])
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  submitForm() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.isLoading = true;
      this.sendEmail(this.form.value as EmailResetDto);
    }
  }

  /**
   * Sends an email to the user with a link to reset the password
   *
   * @param emailResetDto data for sending the email
   */
  sendEmail(emailResetDto: EmailResetDto) {
    console.log('Try to send email for user: ' + emailResetDto.email);
    this.userService.sendPasswordResetEmail(emailResetDto).subscribe({
      next: () => {
        console.log('Successfully sent email for user: ' + emailResetDto.email);
        this.toastService.showSuccess('Success', 'If the user exists, an email has been sent to ' + emailResetDto.email);
        this.router.navigate(['/management/users']);
      },
      error: err => {
        this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
        this.isLoading = false;
      }
    });
  }

}
