import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private _selectedTickets: number[];
  private _selectedSeats: Map<String, boolean> = new Map<String, boolean>();
  private _selectedStanding: number = 0;

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
}
