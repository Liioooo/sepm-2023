import { Component } from '@angular/core';
import { ToastService } from '../../services/toast.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-toasts',
  templateUrl: './toasts.component.html',
  styleUrls: ['./toasts.component.scss']
})
export class ToastsComponent {
  constructor(public toastService: ToastService, private domSanitizer: DomSanitizer) {
  }

  asHtml(content: string) {
    return this.domSanitizer.bypassSecurityTrustHtml(content);
  }
}
