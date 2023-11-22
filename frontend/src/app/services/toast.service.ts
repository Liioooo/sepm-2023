import { Injectable } from '@angular/core';

export interface ToastInfo {
  header: string;
  body: string;
  delay: number;
  classname?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private _toasts: ToastInfo[] = [];

  get toasts() {
    return this._toasts;
  }

  showInfo(header: string, body: string, delay: number = 5000) {
    this._toasts.push({ header, body, delay, classname: 'bg-info' });
  }

  showSuccess(header: string, body: string, delay: number = 5000) {
    this._toasts.push({ header, body, delay, classname: 'bg-success text-white' });
  }

  showWarning(header: string, body: string, delay: number = 5000) {
    this._toasts.push({ header, body, delay, classname: 'bg-warning' });
  }

  showError(header: string, body: string, delay: number = 5000) {
    this._toasts.push({ header, body, delay, classname: 'bg-danger text-white' });
  }

  remove(toast: ToastInfo) {
    this._toasts = this._toasts.filter(t => t != toast);
  }
}
