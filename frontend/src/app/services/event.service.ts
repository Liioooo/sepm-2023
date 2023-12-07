import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Globals } from '../global/globals';
import { EventSearchDto } from '../dtos/event-search-dto';
import { EventDetailDto } from '../dtos/event-detail-dto';
import { convertToDatesInObject } from '../utils/convertToDatesInObject';
import { convertFromDatesInObject } from '../utils/convertFromDatesInObject';
import { removeNullOrUndefinedProps } from '../utils/removeNullOrUndefinedProps';
import { PageableRequest } from '../types/pageable-request';
import { PageDto } from '../dtos/page-dto';
import {TopTenEvents} from "../dtos/topTenEvents";
import {TopTenEventsSearch} from "../dtos/topTenEventsSearch";

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


  getEvent(id: number): Observable<EventDetailDto> {
    return this.httpClient.get<EventDetailDto>(`${this.baseUri}/${id}`).pipe(
      map(convertToDatesInObject)
    );
  }

  getTopEvents(search: TopTenEventsSearch): Observable<TopTenEvents[]> {
    const params = new HttpParams()
      .set('typeId', search.typeId?.toString() || '')
      .set('month', search.month ? JSON.stringify(search.month) : '');

    console.log('Loading the top 10 events with search query:', params.toString());

    const url = `${this.baseUri}/top10`;
    return this.httpClient.get<TopTenEvents[]>(url, { params });
  }
}
