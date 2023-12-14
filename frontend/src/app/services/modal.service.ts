import { Injectable, Type } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ModalContent } from '../components/modal/modal-content';

export const MODAL_DISMISSED = Symbol('MODAL_DISMISSED');

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private bootstrapModalService: NgbModal) {
  }

  async showModal<I, O>(modalContentComponent: Type<ModalContent<I, O>>, inputForModal: I, options?: NgbModalOptions): Promise<O | typeof MODAL_DISMISSED> {
    const modal = this.bootstrapModalService.open(modalContentComponent, options);
    (modal.componentInstance as ModalContent<I, O>).input = inputForModal;

    try {
      return await modal.result;
    } catch (e) {
      return MODAL_DISMISSED;
    }
  }
}
