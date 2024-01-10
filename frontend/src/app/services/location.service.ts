import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { convertFromDatesInObject } from '../utils/convertFromDatesInObject';
import { removeNullOrUndefinedProps } from '../utils/removeNullOrUndefinedProps';
import { LocationSearchDto } from '../dtos/location-search-dto';
import { LocationDetailDto } from '../dtos/location-detail-dto';
import { PageDto } from '../dtos/page-dto';
import { PageableRequest } from '../types/pageable-request';
import { LocationCreateDto } from '../dtos/location-create-dto';
import { LocationHallsDto } from '../dtos/location-halls-dto';
import { HallRowsDetailDto } from '../dtos/hall-rows-detail-dto';

@Injectable({
  providedIn: 'root'
})
export class LocationsService {

  private baseUri: string = this.globals.backendUri + '/locations';
  private adminUri: string = this.globals.backendUri + '/management';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Get Locations from backend, optionally filtered by search parameters
   *
   * @param search Optional: Search by parameters in LocationSearchDto
   * @param pageable Optional: Pageable data
   */
  getLocations(search: LocationSearchDto | null, pageable?: PageableRequest): Observable<PageDto<LocationDetailDto>> {
    const searchParams = search ? convertFromDatesInObject(removeNullOrUndefinedProps(search as {
      [key: string]: string
    })) : {};

    return this.httpClient.get<PageDto<LocationDetailDto>>(this.baseUri, {
      params: {
        ...searchParams,
        ...pageable
      }
    });
  }

  /**
   * Get a Location from backend
   *
   * @param id the id of the location to get
   */
  getLocationById(id: number): Observable<LocationDetailDto> {
    return this.httpClient.get<LocationDetailDto>(`${this.baseUri}/${id}`);
  }

  /**
   * Get all halls from backend
   */
  getAllHalls(): Observable<HallRowsDetailDto[]> {
    return this.httpClient.get<HallRowsDetailDto[]>(`${this.adminUri}/halls`);
  }

  /**
   * Get a Location with its Halls from backend
   *
   * @param id the id of the location to get
   */
  getLocationWithHallsById(id: number): Observable<LocationHallsDto> {
    return this.httpClient.get<LocationHallsDto>(`${this.baseUri}/${id}?includeHalls=true`);
  }

  createLocation(locationCreateDto: LocationCreateDto): Observable<LocationDetailDto> {
    return this.httpClient.post<LocationDetailDto>(`${this.baseUri}`, locationCreateDto);
  }
}
