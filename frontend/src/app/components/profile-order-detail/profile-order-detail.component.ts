import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderDetailDto } from '../../dtos/order-detail-dto';
import { firstValueFrom } from 'rxjs';
import { OrderService } from '../../services/order.service';
import { ErrorResponseDto } from '../../dtos/error-response-dto';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';
import { EventDetailDto } from '../../dtos/event-detail-dto';
import { TicketCategory } from '../../types/ticket-category';
import { OrderType } from '../../types/order-type';
import { EmbeddedFileDto } from '../../dtos/embedded-file-dto';
import { EmbeddedFileService } from '../../services/embedded-file.service';
import {
  ConfirmDeleteReservationModalComponent
} from '../modal/confirm-delete-reservation-modal/confirm-delete-reservation-modal.component';
import { MODAL_DISMISSED, ModalService } from '../../services/modal.service';

@Component({
  selector: 'app-profile-order-detail',
  templateUrl: './profile-order-detail.component.html',
  styleUrls: ['./profile-order-detail.component.scss']
})
export class ProfileOrderDetailComponent implements OnInit {

  private orderId: number;
  public order: OrderDetailDto | undefined;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService,
    private router: Router,
    private embeddedFileService: EmbeddedFileService,
    private modalService: ModalService
  ) {
  }

  async ngOnInit() {
    this.orderId = Number(this.route.snapshot.paramMap.get('orderId'));
    this.order = await firstValueFrom(this.orderService.getOrder(this.orderId)).catch(
      err => {
        this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
        this.router.navigate(['/profile']);
      }
    ) as OrderDetailDto;
  }

  artistName(event: EventDetailDto): string {
    return event.artist.fictionalName || (`${event.artist.firstname} ${event.artist.lastname}`);
  }

  get isReservation() {
    return this.order.orderType === OrderType.RESERVE;
  }

  get isExpired() {
    return this.order.orderType === OrderType.RESERVE && (this.order.event.startDate.getTime() - Date.now()) < 1000 * 60 * 30;
  }

  get isPast() {
    return Date.now() > this.order.event.startDate.getTime();
  }

  categoryToString(category: TicketCategory): string {
    if (category === TicketCategory.SEATING) {
      return 'Seating';
    } else if (category === TicketCategory.STANDING) {
      return 'Standing';
    } else {
      return 'Unknown';
    }
  }

  getPrice(ticket: TicketCategory): number {
    if (ticket === TicketCategory.SEATING) {
      return this.order.event.seatPrice;
    } else if (ticket === TicketCategory.STANDING) {
      return this.order.event.standingPrice;
    } else {
      return 0;
    }
  }

  downloadFile(file: EmbeddedFileDto) {
    this.embeddedFileService.openEmbeddedFile(file);
  }

  async deleteReservation() {
    const shouldDelete = await this.modalService.showModal(ConfirmDeleteReservationModalComponent, this.order.event.title);

    if (shouldDelete === false || shouldDelete === MODAL_DISMISSED) {
      return;
    }

    await this.router.navigate(['/profile']);
  }

}
