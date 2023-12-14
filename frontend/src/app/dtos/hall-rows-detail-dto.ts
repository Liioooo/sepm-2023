import { LocationDetailDto } from './location-detail-dto';
import { RowListDto } from './row-list-dto';

export interface HallRowsDetailDto {
  id: number;
  name: string;
  standingCount: number;
  location: LocationDetailDto;
  rows: RowListDto[];
}
