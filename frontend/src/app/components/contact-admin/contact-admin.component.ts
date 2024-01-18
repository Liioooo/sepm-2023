import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ControlsOf } from '../../types/controls-of';
import { UserLoginDto } from '../../dtos/user-login-dto';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';

@Component({
  selector: 'app-contact-admin',
  templateUrl: './contact-admin.component.html',
  styleUrls: ['./contact-admin.component.scss']
})
export class ContactAdminComponent {

  loginForm: FormGroup<ControlsOf<UserLoginDto>>;

  constructor(
    private formBuilder: FormBuilder
  ) {
    this.loginForm = this.formBuilder.group<ControlsOf<UserLoginDto>>({
      email: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.required, Validators.minLength(8)])
    });
  }
  loginUser() {
    this.loginForm.markAllAsTouched();
  }

}
