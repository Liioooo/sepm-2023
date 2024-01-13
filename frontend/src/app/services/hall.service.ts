import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { HallCreateDto } from '../dtos/hall-create-dto';

@Injectable({
  providedIn: 'root'
})
export class HallService {

  private baseUri: string = this.globals.backendUri + '/halls';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Create a new location and persist it in the backend
   * @param hallCreateDto
   */
  createHall(hallCreateDto: HallCreateDto): Observable<HallCreateDto> {
    return this.httpClient.post<HallCreateDto>(`${this.baseUri}`, hallCreateDto);
  }
}
