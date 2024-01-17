import { RowListDto } from './row-list-dto';

export interface HallCreateDto {
  name: string;
  standingCount: number;
  locationId: number;
  rows: RowListDto[];
}
