import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { OrderCreateDto } from '../dtos/order-create-dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseUri: string = this.globals.backendUri + '/orders';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Get Events from backend, optionally filtered by search parameters
   *
   * @param orderCreateDto Order to create
   */
  createOrder(orderCreateDto: OrderCreateDto): Observable<void> {
    return this.httpClient.post<void>(this.baseUri, orderCreateDto);
  }

}
