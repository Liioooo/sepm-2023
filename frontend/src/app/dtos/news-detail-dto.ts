import { PublicFileDto } from './public-file-dto';

export interface NewsDetailDto {
  id: number;
  title: string;
  text: string;
  overviewText: string;
  publishDate: Date;
  authorName: string;
  image: PublicFileDto
}
