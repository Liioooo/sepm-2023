import { Component } from '@angular/core';
import { EventDetailDto } from '../../../../dtos/event-detail-dto';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import { ToastService } from '../../../../services/toast.service';
import { Router } from '@angular/router';
import { EventService } from '../../../../services/event.service';
import { ErrorResponseDto } from '../../../../dtos/error-response-dto';
import { ErrorFormatterService } from '../../../../services/error-formatter.service';

@Component({
  selector: 'app-event-create',
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.scss']
})
export class EventCreateComponent {

  public event: EventDetailDto;

  constructor(
    private notification: ToastService,
    private router: Router,
    private service: EventService,
    private errorFormatterService: ErrorFormatterService) {
  }

  public onSubmit(form: NgForm): void {
    if (form.valid) {
      let observable: Observable<EventDetailDto>;
      observable = this.service.createEvent(this.event);
      observable.subscribe({
        next: data => {
          this.notification.showSuccess('Success', 'Event successfully created');
          this.router.navigate(['/management/events']);
        },
        error: err => {
          this.notification
            .showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
        }
      });
    }
  }
}
