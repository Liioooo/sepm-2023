import { Injectable, SecurityContext } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ErrorResponseDto } from '../dtos/error-response-dto';

@Injectable({
  providedIn: 'root'
})
export class ErrorFormatterService {
  constructor(private domSanitizer: DomSanitizer) {
  }

  format(error: ErrorResponseDto | string): string {
    let errorObject: Partial<ErrorResponseDto>;

    if (typeof error === 'string') {
      try {
        errorObject = JSON.parse(error);
      } catch (e) {
        errorObject = {
          error: undefined,
          subErrors: undefined
        };
      }
    } else {
      errorObject = error;
    }

    let message: string = '';
    if (!!errorObject.error) {
      message += `<p>${this.domSanitizer.sanitize(SecurityContext.HTML, errorObject.error)}</p>`;
    }
    if (!!errorObject.subErrors) {
      message += '<ul>';
      for (const e in errorObject.subErrors) {
        const sanE = this.domSanitizer.sanitize(SecurityContext.HTML, errorObject.subErrors[e]);
        message += `<li>${sanE}</li>`;
      }
      message += '</ul>';
    }
    return message;
  }
}
