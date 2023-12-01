import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { EventSearchDto } from '../dtos/event-search-dto';
import { EventDetailDto } from '../dtos/event-detail-dto';
import { convertToDatesInObject } from '../utils/convertToDatesInObject';
import { convertFromDatesInObject } from '../utils/convertFromDatesInObject';
import { removeNullOrUndefinedProps } from '../utils/removeNullOrUndefinedProps';
import { PageableRequest } from '../types/pageable-request';
import { PageDto } from '../dtos/page-dto';

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
}
