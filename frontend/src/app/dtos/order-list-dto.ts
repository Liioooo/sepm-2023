import { OrderType } from '../types/order-type';
import { EmbeddedFileDto } from './embedded-file-dto';
import { EventListDto } from './event-list-dto';

export interface OrderListDto {
  id: number;
  event: EventListDto;
  orderType: OrderType;
  orderDate: Date;
  pdfTickets: EmbeddedFileDto;
  receipt: EmbeddedFileDto;
  cancellationReceipts: EmbeddedFileDto[];
}
