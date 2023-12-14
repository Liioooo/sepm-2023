import { LocationDetailDto } from './location-detail-dto';

export interface HallDetailDto {
  id: number;
  name: string;
  standingCount: number;
  location: LocationDetailDto;
}
