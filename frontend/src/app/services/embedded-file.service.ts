import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../global/globals';
import { EmbeddedFileDto } from '../dtos/embedded-file-dto';
import { ToastService } from './toast.service';

@Injectable({
  providedIn: 'root'
})
export class EmbeddedFileService {

  private baseUri: string = this.globals.backendUri + '/files';

  constructor(private httpClient: HttpClient, private globals: Globals, private toastService: ToastService) {
  }

  /**
   * Opens an embedded file in a new tab
   *
   * @param embeddedFileDto The embedded file to open
   */
  openEmbeddedFile(embeddedFileDto: EmbeddedFileDto): void {
    this.httpClient.get(`${this.baseUri}/${embeddedFileDto.id}`, { responseType: 'arraybuffer' }).subscribe(
      {
        next: (next: any) => {
          let blob = new Blob([next], { type: 'application/pdf' });
          let url = window.URL.createObjectURL(blob);
          let pwa = window.open(url);
          if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
            this.toastService.showError('Could not open invoice PDF', 'Please disable your Pop-up blocker and try again.');
          }
        }
      }
    );
  }
}
