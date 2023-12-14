import { Component } from '@angular/core';
import { ModalContent } from '../modal-content';

@Component({
  selector: 'app-confirm-delete-reservation-modal',
  templateUrl: './confirm-delete-reservation-modal.component.html',
  styleUrls: ['./confirm-delete-reservation-modal.component.scss']
})
export class ConfirmDeleteReservationModalComponent extends ModalContent<string, boolean> {

}
