import { Component, OnInit } from '@angular/core';
import { EventDetailDto } from '../../../../dtos/event-detail-dto';
import { ActivatedRoute } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';
import { EventService } from '../../../../services/event.service';
import { ErrorResponseDto } from '../../../../dtos/error-response-dto';
import { ErrorFormatterService } from '../../../../services/error-formatter.service';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit {
  public event: EventDetailDto;

  constructor(
    private route: ActivatedRoute,
    private notification: ToastService,
    private service: EventService,
    private errorFormatterService: ErrorFormatterService
  ) {
  }

  ngOnInit(): void {
    this.loadEvent();
  }

  loadEvent() {
    this.service.getEvent(Number(this.route.snapshot.paramMap.get('id'))).subscribe({
      next: event => {
        this.event = event;
      },
      error: err => {
        this.notification
          .showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
      }
    });
  }

}
