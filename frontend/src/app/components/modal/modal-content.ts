import { Directive, inject, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Directive()
export abstract class ModalContent<I = any, O = any> {

  @Input() input: I;

  private thisModal = inject(NgbActiveModal);

  /**
   * Will close the modal and ModalService.showModal(...) will return the value passed to this function
   */
  closeModal(output: O) {
    this.thisModal.close(output);
  }

  /**
   * Will dismiss the modal and ModalService.showModal(...) will return MODAL_DISMISSED
   */
  dismissModal() {
    this.thisModal.dismiss();
  }
}
