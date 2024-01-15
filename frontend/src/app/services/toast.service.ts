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
    this._toasts.push({ header, body, delay, classname: 'text-bg-info' });
  }

  showSuccess(header: string, body: string, delay: number = 5000) {
    this._toasts.push({ header, body, delay, classname: 'text-bg-success' });
  }

  showWarning(header: string, body: string, delay: number = 5000) {
    this._toasts.push({ header, body, delay, classname: 'text-bg-warning' });
  }

  showError(header: string, body: string, delay: number = 5000) {
    this._toasts.push({ header, body, delay, classname: 'text-bg-danger' });
  }

  remove(toast: ToastInfo) {
    this._toasts = this._toasts.filter(t => t != toast);
  }
}
