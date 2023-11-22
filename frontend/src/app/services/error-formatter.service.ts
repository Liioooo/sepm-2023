import { Injectable, SecurityContext } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ErrorResponseDto } from '../dtos/error-response-dto';

@Injectable({
  providedIn: 'root'
})
export class ErrorFormatterService {
  constructor(private domSanitizer: DomSanitizer) {
  }

  format(error: ErrorResponseDto): string {
    let message: string = '';
    console.log(error);
    if (!!error.error) {
      message += `<p>${this.domSanitizer.sanitize(SecurityContext.HTML, error.error)}</p>`;
    }
    if (!!error.subErrors) {
      message += '<ul>';
      for (const e in error.subErrors) {
        const sanE = this.domSanitizer.sanitize(SecurityContext.HTML, error.subErrors[e]);
        message += `<li>${sanE}</li>`;
      }
      message += '</ul>';
    }
    return message;
  }
}
