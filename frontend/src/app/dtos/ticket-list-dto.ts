import { TicketCategory } from '../types/ticket-category';
import { EmbeddedFileDto } from './embedded-file-dto';

export interface TicketListDto {
  id: number;
  ticketCategory: TicketCategory;
  rowNumber: number;
  seatNumber: number;
  pdfTicket: EmbeddedFileDto;
}
