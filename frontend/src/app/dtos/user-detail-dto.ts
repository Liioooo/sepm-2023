import { UserLocationDto } from './user-location-dto';

export interface UserDetailDto {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  isLocked: boolean;
  location?: UserLocationDto;
}
