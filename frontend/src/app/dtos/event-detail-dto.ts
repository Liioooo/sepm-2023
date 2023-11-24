import { ArtistDetailDto } from './artist-detail-dto';
import { HallDetailDto } from './hall-detail-dto';

export interface EventDetailDto {
  id: number;
  title: string;
  date: Date;
  seatPrice: number;
  standingPrice: number;
  artist: ArtistDetailDto;
  hall: HallDetailDto;
}
