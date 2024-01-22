import { UserLocationDto } from './user-location-dto';
import { UserRole } from '../enums/userRole';

export interface UserDetailDto {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  isLocked: boolean;
  role: UserRole;
  location?: UserLocationDto;
}
