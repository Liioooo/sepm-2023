import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { convertFromDatesInObject } from '../utils/convertFromDatesInObject';
import { removeNullOrUndefinedProps } from '../utils/removeNullOrUndefinedProps';
import { LocationSearchDto } from '../dtos/location-search-dto';
import { LocationDetailDto } from '../dtos/location-detail-dto';

@Injectable({
  providedIn: 'root'
})
export class LocationsService {

  private baseUri: string = this.globals.backendUri + '/locations';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Get Locations from backend, optionally filtered by search parameters
   *
   * @param search Optional: Search by parameters in LocationSearchDto
   */
  getLocations(search: LocationSearchDto | null): Observable<LocationDetailDto[]> {
    return this.httpClient.get<LocationDetailDto[]>(this.baseUri, {
      params: search ? convertFromDatesInObject(removeNullOrUndefinedProps(search as { [key: string]: string })) : {}
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

}
