import { TicketCategory } from '../types/ticket-category';

export interface TicketCreateDto {
  ticketCategory: TicketCategory;
  seatNumber?: number;
  tierNumber?: number;
}
