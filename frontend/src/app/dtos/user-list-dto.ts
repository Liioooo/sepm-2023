import { UserRole } from '../enums/userRole';

export interface UserListDto {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  isLocked: boolean;
  role: UserRole;
}
