import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Globals} from "../global/globals";
import {map, Observable} from "rxjs";
import {convertToDatesInObject} from "../utils/convertToDatesInObject";
import {NewsDetailDto} from "../dtos/news-detail-dto";
import {PageableRequest} from "../types/pageable-request";
import {PageDto} from "../dtos/page-dto";
import {NewsListDto} from "../dtos/news-list-dto";
import {NewsReqType} from "../enums/newsReqType";

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private baseUri: string = this.globals.backendUri + '/news';
  private readUri: string = this.baseUri + 'read';
  private unReadUri: string = this.baseUri + 'unread';

  constructor(
    private httpClient: HttpClient,
    private globals: Globals) {

  }

  private getUriFromReqType(reqType: NewsReqType): string {
    switch (reqType) {
      case NewsReqType.Read:
        return this.readUri;
      case NewsReqType.Unread:
        return this.unReadUri;
      default:
        return this.unReadUri;
    }
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
      map(convertToDatesInObject)
    );
  }

  /**
   * Get a single news entry from the backend
   * @param id The id of the news to load
   */
  getNews(id: number): Observable<NewsDetailDto> {
    return this.httpClient.get<NewsDetailDto>(`${this.baseUri}/${id}`).pipe(
      map(convertToDatesInObject)
    );
  }

  createNews() {

  }
}
