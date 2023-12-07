import { ArtistDetailDto } from './artist-detail-dto';
import { HallTiersDetailDto } from './hall-tiers-detail-dto';

export interface EventDetailDto {
  id: number;
  title: string;
  startDate: Date;
  endDate: Date;
  seatPrice: number;
  standingPrice: number;
  artist: ArtistDetailDto;
  hall: HallTiersDetailDto;
}
