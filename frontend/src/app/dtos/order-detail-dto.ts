import { OrderType } from '../types/order-type';
import { EmbeddedFileDto } from './embedded-file-dto';
import { EventDetailDto } from './event-detail-dto';
import { TicketListDto } from './ticket-list-dto';

export interface OrderDetailDto {
  id: number;
  event: EventDetailDto;
  tickets: TicketListDto[];
  orderType: OrderType;
  orderDate: Date;
  pdfTickets: EmbeddedFileDto;
  receipt: EmbeddedFileDto;
  cancellationReceipts: EmbeddedFileDto[];
}
