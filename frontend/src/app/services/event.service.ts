import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Globals } from '../global/globals';
import { EventSearchDto } from '../dtos/event-search-dto';
import { EventDetailDto } from '../dtos/event-detail-dto';
import { convertToDatesInObject } from '../utils/convertToDatesInObject';
import { convertFromDatesInObject } from '../utils/convertFromDatesInObject';
import { removeNullOrUndefinedProps } from '../utils/removeNullOrUndefinedProps';
import { PageableRequest } from '../types/pageable-request';
import { PageDto } from '../dtos/page-dto';
import { TopTenEventSearchDto } from '../dtos/top-ten-event-search-dto';
import { EventWithBoughtCountDto } from '../dtos/event-with-bought-count-dto';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private baseUri: string = this.globals.backendUri + '/events';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Get Events from backend, optionally filtered by search parameters
   *
   * @param search Optional: Search by parameters in EventSearchDto
   * @param pageable Optional: Pageable data
   */
  getEvents(search: EventSearchDto | null, pageable?: PageableRequest): Observable<PageDto<EventDetailDto>> {
    const searchParams = search ? convertFromDatesInObject(removeNullOrUndefinedProps(search as {
      [key: string]: string
    })) : {};

    return this.httpClient.get<PageDto<EventDetailDto>>(this.baseUri, {
      params: {
        ...searchParams,
        ...pageable
      }
    }).pipe(
      map(convertToDatesInObject)
    );
  }

  /**
   * Get a single event from the backend
   * @param id The id of the event to load
   */
  getEvent(id: number): Observable<EventDetailDto> {
    return this.httpClient.get<EventDetailDto>(`${this.baseUri}/${id}`).pipe(
      map(convertToDatesInObject)
    );
  }

  getTopEvents(search: TopTenEventSearchDto): Observable<EventWithBoughtCountDto[]> {
    const params = new HttpParams()
      .set('month', search.month)
      .set('eventType', search.type || '');
    const url = `${this.baseUri}/top10`;
    return this.httpClient.get<EventWithBoughtCountDto[]>(url, { params });
  }
}
