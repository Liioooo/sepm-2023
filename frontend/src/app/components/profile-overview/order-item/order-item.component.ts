import { Component, EventEmitter, Input, Output } from '@angular/core';
import { OrderListDto } from '../../../dtos/order-list-dto';
import { OrderType } from '../../../types/order-type';
import { MODAL_DISMISSED, ModalService } from '../../../services/modal.service';
import {
  ConfirmDeleteReservationModalComponent
} from '../../modal/confirm-delete-reservation-modal/confirm-delete-reservation-modal.component';
import { EmbeddedFileDto } from '../../../dtos/embedded-file-dto';
import { EmbeddedFileService } from '../../../services/embedded-file.service';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.scss']
})
export class OrderItemComponent {

  @Input() order!: OrderListDto;

  @Output() deleteReservationEvent = new EventEmitter<OrderListDto>();

  constructor(private modalService: ModalService, private embeddedFileService: EmbeddedFileService) {
  }

  get event() {
    return this.order.event;
  }

  get artist() {
    return this.event.artist.fictionalName || (`${this.event.artist.firstname} ${this.event.artist.lastname}`);
  }

  get isReservation() {
    return this.order.orderType === OrderType.RESERVE;
  }

  get isExpired() {
    return this.order.orderType === OrderType.RESERVE && (this.order.event.startDate.getTime() - Date.now()) < 1000 * 60 * 30;
  }

  async deleteReservation() {
    const shouldDelete = await this.modalService.showModal(ConfirmDeleteReservationModalComponent, this.order.event.title);

    if (shouldDelete === false || shouldDelete === MODAL_DISMISSED) {
      return;
    }

    this.deleteReservationEvent.emit(this.order);
  }

  downloadTicket(pdfTickets: EmbeddedFileDto) {
    this.embeddedFileService.openEmbeddedFile(pdfTickets);
  }
}
