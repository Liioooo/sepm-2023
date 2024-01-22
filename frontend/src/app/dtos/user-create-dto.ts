import { UserLocationDto } from './user-location-dto';
import { UserRole } from '../enums/userRole';

export interface UserCreateDto {
  email: string;
  firstName: string;
  lastName: string;
  password?: string;
  confirmPassword?: string;
  location?: UserLocationDto;
  isLocked: boolean,
  role: UserRole
}
