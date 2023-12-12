import { Injectable } from '@angular/core';
import { PageableRequest } from '../types/pageable-request';
import { map, Observable } from 'rxjs';
import { PageDto } from '../dtos/page-dto';
import { convertToDatesInObject } from '../utils/convertToDatesInObject';
import { removeNullOrUndefinedProps } from '../utils/removeNullOrUndefinedProps';
import { NewsSearchDto } from '../dtos/news-search-dto';
import { NewsDetailDto } from '../dtos/news-detail-dto';
import { Globals } from '../global/globals';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private usersBaseUri: string = this.globals.backendUri + '/management/news';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getNews(search: NewsSearchDto | null, pageable?: PageableRequest): Observable<PageDto<NewsDetailDto>> {
    const searchParams = search ? convertToDatesInObject(removeNullOrUndefinedProps(search as {
      [key: string]: string
    })) : {};

    return this.httpClient.get<PageDto<NewsDetailDto>>(this.usersBaseUri, {
      params: {
        ...searchParams,
        ...pageable
      }
    }).pipe(
      map(convertToDatesInObject)
    );
  }

}
