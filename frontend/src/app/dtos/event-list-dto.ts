import { ArtistDetailDto } from './artist-detail-dto';
import { HallDetailDto } from './hall-detail-dto';

export interface EventListDto {
  id: number;
  title: string;
  startDate: Date;
  endDate: Date;
  seatPrice: number;
  standingPrice: number;
  artist: ArtistDetailDto;
  hall: HallDetailDto;
  type: string;
}

