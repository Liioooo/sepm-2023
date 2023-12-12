import { TicketCategory } from '../types/ticket-category';

export interface TicketListDto {
  id: number;
  ticketCategory: TicketCategory;
  tierNumber: number;
  seatNumber: number;
}
