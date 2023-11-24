export interface EventSearchDto {
  search?: string;
  artist?: string;
  title?: string;
  location?: string;
  timeStart?: Date;
  timeEnd?: Date;
  priceMax?: number;
}
