import { EventListDto } from './event-list-dto';

export interface EventWithBoughtCountDto {
  event: EventListDto;
  boughtCount: number;
}
