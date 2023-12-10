import { Injectable } from '@angular/core';
import { map, Observable, pipe } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { OrderCreateDto } from '../dtos/order-create-dto';
import { OrderListDto } from '../dtos/order-list-dto';
import { convertToDatesInObject } from '../utils/convertToDatesInObject';
import { OrderDetailDto } from '../dtos/order-detail-dto';
import { OrderType } from '../types/order-type';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseUri: string = this.globals.backendUri + '/orders';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Create a new order
   *
   * @param orderCreateDto Order to create
   */
  createOrder(orderCreateDto: OrderCreateDto): Observable<void> {
    return this.httpClient.post<void>(this.baseUri, orderCreateDto);
  }

  /**
   * Get an order by its id
   * @param orderId
   */
  getOrder(orderId: number): Observable<OrderDetailDto> {
    return this.httpClient.get<OrderDetailDto>(`${this.baseUri}/${orderId}`).pipe(
      map(convertToDatesInObject)
    );
  }

  /**
   * Get all orders of the current user
   */
  getOwnOrders(): Observable<OrderListDto[]> {
    return this.httpClient.get<OrderListDto[]>(this.baseUri).pipe(
      map(convertToDatesInObject)
    );
  }

  /**
   * Delete a reservation by its id
   * @param orderId
   */
  purchaseReservation(orderId: number): Observable<void> {
    return this.httpClient.patch<void>(`${this.baseUri}/${orderId}`, {
      orderType: OrderType.BUY
    });
  }

  /**
   * Delete a reservation by its id
   * @param orderId
   */
  deleteReservation(orderId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUri}/${orderId}`);
  }

}
