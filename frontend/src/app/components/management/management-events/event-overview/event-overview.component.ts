import { Component } from '@angular/core';
import { EventDetailDto } from '../../../../dtos/event-detail-dto';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';
import { EventService } from '../../../../services/event.service';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent {
  public event: EventDetailDto;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private notification: ToastService,
    private service: EventService
  ) {
    this.route.data.subscribe(
      data => {
        const id = Number(this.route.snapshot.paramMap.get('id'));
        if (Number.isNaN(id)) {
          this.notification.showError('Could not load Event', 'This event could not be found');
          this.router.navigate(['/management/events']);
        } else {
          this.service.getEvent(id).subscribe({
            next: event => {
              this.event = event;
            }
          });
        }
      });
  }
}
