import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { EventSearchDto } from '../dtos/event-search-dto';
import { EventDetailDto } from '../dtos/event-detail-dto';
import { convertToDatesInObject } from '../utils/convertToDatesInObject';
import { convertFromDatesInObject } from '../utils/convertFromDatesInObject';
import { removeNullOrUndefinedProps } from '../utils/removeNullOrUndefinedProps';

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
   */
  getEvents(search: EventSearchDto | null): Observable<EventDetailDto[]> {
    return this.httpClient.get<EventDetailDto[]>(this.baseUri, {
      params: search ? convertFromDatesInObject(removeNullOrUndefinedProps(search as { [key: string]: string })) : {}
    }).pipe(
      map(convertToDatesInObject)
    );
  }
}
