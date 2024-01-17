import { EventType } from '../enums/eventType';

export class EventCreateDto {
  title: string;
  startDate: Date;
  endDate: Date;
  seatPrice: number;
  standingPrice: number;
  hallId: number;
  artistId: number;
  type: EventType;
  image: File;
}
