import { OrderType } from '../types/order-type';
import { EmbeddedFileDto } from './embedded-file-dto';
import { EventDetailDto } from './event-detail-dto';

export interface OrderListDto {
  id: number;
  event: EventDetailDto;
  orderType: OrderType;
  orderDate: Date;
  cancellationDate: Date;
  pdfTickets: EmbeddedFileDto;
  receipt: EmbeddedFileDto;
  cancellationReceipt: EmbeddedFileDto;
}
