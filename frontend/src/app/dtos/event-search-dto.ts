export interface EventSearchDto {
  search?: string;
  artist?: string;
  title?: string;
  locationId?: string;
  timeStart?: Date;
  timeEnd?: Date;
  priceMax?: number;
}
