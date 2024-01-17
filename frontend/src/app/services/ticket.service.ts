import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { TicketListDto } from '../dtos/ticket-list-dto';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { convertToDatesInObject } from '../utils/convertToDatesInObject';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private _selectedTickets: number[];
  private _selectedSeats: Map<String, boolean> = new Map<String, boolean>();
  private _selectedStanding: number = 0;

  private baseUri: string = this.globals.backendUri + '/tickets/verify';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }


  set selectedSeats(value: Map<String, boolean>) {
    this._selectedSeats = value;
  }

  set selectedStanding(value: number) {
    this._selectedStanding = value;
  }

  get selectedSeats(): Map<String, boolean> {
    return this._selectedSeats;
  }

  get selectedStanding(): number {
    return this._selectedStanding;
  }

  get selectedTickets(): number[] {
    return this._selectedTickets ?? [];
  }

  set selectedTickets(value: number[]) {
    this._selectedTickets = value;
  }

  /**
   * Get ticket by UUID
   * @param uuid
   */
  getTicketByUuid(uuid: string): Observable<TicketListDto> {
    return this.httpClient.get<TicketListDto>(`${this.baseUri}/${uuid}`).pipe(
      map(convertToDatesInObject)
    );
  }

}
