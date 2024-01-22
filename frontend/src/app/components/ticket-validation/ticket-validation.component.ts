import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { TicketListDto } from '../../dtos/ticket-list-dto';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs';

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
    private route: ActivatedRoute
  ) {

  }

  ngOnInit() {
    this.verified = false;

    this.route.paramMap.pipe(
      switchMap(params => this.service.getTicketByUuid(String(params.get('uuid'))))).subscribe(
      {
        next: ticket => {
          this.ticket = ticket;
          this.verified = true;
        },
        error: () => {
          this.ticket = null;
          this.verified = false;
        }
      }
    );
  }

}
