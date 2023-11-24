import { Component } from '@angular/core';
import { ModalContent } from '../modal-content';

@Component({
  selector: 'app-confirm-delete-profile-modal',
  templateUrl: './confirm-delete-profile-modal.component.html',
  styleUrls: ['./confirm-delete-profile-modal.component.scss']
})
export class ConfirmDeleteProfileModalComponent extends ModalContent<undefined, boolean> {

}
