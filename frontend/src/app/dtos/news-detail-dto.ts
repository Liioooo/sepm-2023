import { UserDetailDto } from './user-detail-dto';

export interface NewsDetailDto {
  id: number;
  title: string;
  author: UserDetailDto;
  publishDate: Date;
}
