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
import { EventCreateDto } from '../dtos/event-create-dto';
import { ArtistDetailDto } from '../dtos/artist-detail-dto';
import { EventListDto } from '../dtos/event-list-dto';
import { convertPublicFileUrlToAbsoluteUrl } from '../utils/convertFromPublicFileUrlToAbsoluteUrl';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private baseUri: string = this.globals.backendUri + '/events';
  private adminUri: string = this.globals.backendUri + '/management';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Get Events from backend, optionally filtered by search parameters
   *
   * @param search Optional: Search by parameters in EventSearchDto
   * @param pageable Optional: Pageable data
   */
  getEvents(search: EventSearchDto | null, pageable?: PageableRequest): Observable<PageDto<EventListDto>> {
    const searchParams = search ? convertFromDatesInObject(removeNullOrUndefinedProps(search as {
      [key: string]: string
    })) : {};

    return this.httpClient.get<PageDto<EventListDto>>(this.baseUri, {
      params: {
        ...searchParams,
        ...pageable
      }
    }).pipe(
      map(convertToDatesInObject),
      map((page) => {
        page.content = page.content.map((event) => {
          event.image = convertPublicFileUrlToAbsoluteUrl(event.image, this.globals.backendBaseUri);
          return event;
        });
        return page;
      })
    );
  }

  /**
   * Get a single event from the backend
   * @param id The id of the event to load
   */
  getEvent(id: number): Observable<EventDetailDto> {
    return this.httpClient.get<EventDetailDto>(`${this.baseUri}/${id}`).pipe(
      map(convertToDatesInObject),
      map(event => {
        event.image = convertPublicFileUrlToAbsoluteUrl(event.image, this.globals.backendBaseUri);
        return event;
      })
    );
  }


  getEventsForManagement(search: EventSearchDto | null, pageable?: PageableRequest): Observable<PageDto<EventDetailDto>> {
    const searchParams = search ? convertFromDatesInObject(removeNullOrUndefinedProps(search as {
      [key: string]: string
    })) : {};

    return this.httpClient.get<PageDto<EventDetailDto>>(`${this.adminUri}/events`, {
      params: {
        ...searchParams,
        ...pageable
      }
    }).pipe(
      map(convertToDatesInObject)
    );
  }

  /**
   * Create new event
   * @param eventCreate event to create
   */
  createEvent(eventCreate: EventCreateDto): Observable<EventCreateDto> {
    let startDate = new Date(eventCreate.startDate);
    let endDate = new Date(eventCreate.endDate);

    const formData: FormData = new FormData();
    formData.append('title', eventCreate.title);
    formData.append('startDate', startDate.toISOString());
    formData.append('endDate', endDate.toISOString());
    formData.append('seatPrice', eventCreate.seatPrice.toString());
    formData.append('standingPrice', eventCreate.standingPrice.toString());
    formData.append('hallId', eventCreate.hallId.toString());
    formData.append('artistId', eventCreate.artistId.toString());
    formData.append('type', eventCreate.type);
    formData.append('image', eventCreate.image);

    return this.httpClient.post<EventCreateDto>(`${this.baseUri}`, formData);
  }

  /**
   * Get all artists from backend
   */
  getAllArtists(): Observable<ArtistDetailDto[]> {
    return this.httpClient.get<ArtistDetailDto[]>(`${this.adminUri}/artists`);
  }

  getTopEvents(search: TopTenEventSearchDto): Observable<EventWithBoughtCountDto[]> {
    const params = new HttpParams()
      .set('month', search.month)
      .set('eventType', search.type || '');
    const url = `${this.baseUri}/top10`;
    return this.httpClient.get<EventWithBoughtCountDto[]>(url, { params });
  }
}
