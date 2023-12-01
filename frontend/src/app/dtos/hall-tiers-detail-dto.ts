import { LocationDetailDto } from './location-detail-dto';
import { TierListDto } from './tier-list-dto';

export interface HallTiersDetailDto {
  id: number;
  name: string;
  standingCount: number;
  location: LocationDetailDto;
  tiers: TierListDto[];
}
