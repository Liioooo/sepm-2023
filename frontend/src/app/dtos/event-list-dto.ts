import { ArtistDetailDto } from './artist-detail-dto';
import { HallTiersDetailDto }  from './hall-tiers-detail-dto';

export interface EventListDto {
  id: number;
  title: string;
  startDate: Date;
  endDate: Date;
  seatPrice: number;
  standingPrice: number;
  artist: ArtistDetailDto;
  hall: HallTiersDetailDto;
  type: string;
}

