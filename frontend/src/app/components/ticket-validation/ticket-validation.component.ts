import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { TicketListDto } from '../../dtos/ticket-list-dto';
import { ActivatedRoute } from '@angular/router';
import { ErrorResponseDto } from '../../dtos/error-response-dto';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';

@Component({
  selector: 'app-ticket-validation',
  templateUrl: './ticket-validation.component.html',
  styleUrls: ['./ticket-validation.component.scss']
})
export class TicketValidationComponent implements OnInit {
  public ticket: TicketListDto;
  verified: boolean;

  constructor(
    private service: TicketService,
    private route: ActivatedRoute,
    private notification: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {

  }

  ngOnInit() {
    this.verified = false;
    this.service.getTicketByUuid(String(this.route.snapshot.paramMap.get('uuid'))).subscribe({
      next: ticket => {
        this.ticket = ticket;
        this.verified = true;
        this.notification.showSuccess('VALID TICKET', '');
      },
      error: err => {
        this.verified = false;
        this.notification.showError('INVALID TICKET', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
      }
    }
    );
  }

}
