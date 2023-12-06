import { TicketCreateDto } from './ticket-create-dto';
import { OrderType } from '../types/order-type';

export interface OrderCreateDto {
  tickets: TicketCreateDto[];
  orderType: OrderType;
}
