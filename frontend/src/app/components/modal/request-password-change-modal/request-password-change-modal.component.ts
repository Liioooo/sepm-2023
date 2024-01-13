import { Component } from '@angular/core';
import { ModalContent } from '../modal-content';

@Component({
  selector: 'app-request-password-change-modal',
  templateUrl: './request-password-change-modal.component.html',
  styleUrls: ['./request-password-change-modal.component.scss']
})
export class RequestPasswordChangeModalComponent extends ModalContent<undefined, boolean> {
}
