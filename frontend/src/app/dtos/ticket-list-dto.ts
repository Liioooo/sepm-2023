import { TicketCategory } from '../types/ticket-category';

export interface TicketListDto {
  id: number;
  ticketCategory: TicketCategory;
  rowNumber: number;
  seatNumber: number;
}
