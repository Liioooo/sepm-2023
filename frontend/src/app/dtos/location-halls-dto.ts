import { HallTitleDto } from './hall-title-dto';

export interface LocationHallsDto {
  id: number;
  title: string;
  address: string;
  postalCode: string;
  city: string;
  country: string;
  halls: HallTitleDto[];
}
