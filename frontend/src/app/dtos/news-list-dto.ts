import {PublicFileDto} from "./public-file-dto";

export interface NewsListDto {
  id: number;
  title: string;
  overviewText: string;
  publishDate: Date;
  image: PublicFileDto
}
