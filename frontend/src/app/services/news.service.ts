import { Injectable } from '@angular/core';
import { PageableRequest } from '../types/pageable-request';
import { map, Observable } from 'rxjs';
import { PageDto } from '../dtos/page-dto';
import { convertToDatesInObject } from '../utils/convertToDatesInObject';
import { NewsDetailDto } from '../dtos/news-detail-dto';
import { Globals } from '../global/globals';
import { HttpClient } from '@angular/common/http';

import { NewsListDto } from '../dtos/news-list-dto';
import { NewsReqType } from '../enums/newsReqType';
import { NewsCreateDto } from '../dtos/news-create-dto';
import { convertPublicFileUrlToAbsoluteUrl } from '../utils/convertFromPublicFileUrlToAbsoluteUrl';
import { NewsSearchDto } from '../dtos/news-search-dto';
import { removeNullOrUndefinedProps } from '../utils/removeNullOrUndefinedProps';
import { NewsListManagementDto } from '../dtos/news-list-management-dto';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private baseUri: string = this.globals.backendUri + '/news';
  private newsBaseUri: string = this.globals.backendUri + '/management/news';


  constructor(
    private httpClient: HttpClient,
    private globals: Globals) {
  }

  private getUriFromReqType(reqType: NewsReqType): string {
    return `${this.baseUri}/${reqType}`;
  }

  /**
   * Get read news from backend, optionally filtered by search parameters
   *
   * @param reqType
   * @param pageable Optional: Pageable data
   */
  getNewsList(reqType: NewsReqType, pageable?: PageableRequest): Observable<PageDto<NewsListDto>> {
    let uri: string = this.getUriFromReqType(reqType);
    return this.httpClient.get<PageDto<NewsListDto>>(uri, {
      params: {
        ...pageable
      }
    }).pipe(
      map(convertToDatesInObject),
      map((page) => {
        page.content = page.content.map((news) => {
          news.image = convertPublicFileUrlToAbsoluteUrl(news.image, this.globals.backendBaseUri);
          return news;
        });
        return page;
      })
    );
  }

  /**
   * Get a single news entry from the backend
   * @param id The id of the news to load
   */
  getNews(id: number): Observable<NewsDetailDto> {
    return this.httpClient.get<NewsDetailDto>(`${this.baseUri}/${id}`).pipe(
      map(convertToDatesInObject),
      map((news) => {
        news.image = convertPublicFileUrlToAbsoluteUrl(news.image, this.globals.backendBaseUri);
        return news;
      })
    );
  }

  createNews(createDto: NewsCreateDto) {
    const createUri: string = this.baseUri;

    const formData: FormData = new FormData();
    formData.append('title', createDto.title);
    formData.append('overviewText', createDto.overviewText);
    formData.append('text', createDto.text);
    formData.append('image', createDto.image);


    return this.httpClient.post<NewsDetailDto>(createUri, formData);
  }

  findNews(search: NewsSearchDto | null, pageable?: PageableRequest): Observable<PageDto<NewsListManagementDto>> {
    const searchParams = search ? convertToDatesInObject(removeNullOrUndefinedProps(search as {
      [key: string]: string
    })) : {};

    return this.httpClient.get<PageDto<NewsListManagementDto>>(this.newsBaseUri, {
      params: {
        ...searchParams,
        ...pageable
      }
    }).pipe(
      map(convertToDatesInObject)
    );
  }

}
