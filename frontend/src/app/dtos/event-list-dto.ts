import { ArtistDetailDto } from './artist-detail-dto';
import { HallDetailDto } from './hall-detail-dto';
import { PublicFileDto } from './public-file-dto';

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
  image: PublicFileDto;
}

