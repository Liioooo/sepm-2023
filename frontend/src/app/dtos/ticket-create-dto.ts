import { TicketCategory } from '../types/ticket-category';

export interface TicketCreateDto {
  category: TicketCategory;
  seatNumber?: number;
  tierNumber?: number;
  eventId: number;
}
