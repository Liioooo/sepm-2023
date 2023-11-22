import { UserLocationDto } from './user-location-dto';

export interface UpdateUserDetailDto {
  email: string;
  firstName: string;
  lastName: string;
  password?: string;
  confirmPassword?: string;
  location?: UserLocationDto;
}
