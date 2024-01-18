import { ArtistDetailDto } from './artist-detail-dto';
import { HallRowsDetailDto } from './hall-rows-detail-dto';
import { SeatDto } from './seat-dto';
import { PublicFileDto } from './public-file-dto';

export interface EventDetailDto {
  id: number;
  title: string;
  startDate: Date;
  endDate: Date;
  seatPrice: number;
  standingPrice: number;
  artist: ArtistDetailDto;
  hall: HallRowsDetailDto;
  occupiedSeats: SeatDto[];
  occupiedStandings: number;
  image: PublicFileDto;
}
