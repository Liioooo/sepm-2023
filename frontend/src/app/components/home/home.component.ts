import { Component, OnInit } from '@angular/core';
import {TopTenEvents} from "../../dtos/topTenEvents";
import {EventTypeDto} from "../../dtos/eventTypeDto";
import {ToastService} from "../../services/toast.service";
import {EventService} from "../../services/event.service";
import {FormBuilder} from "@angular/forms";
import {ErrorResponseDto} from "../../dtos/error-response-dto";
import {ErrorFormatterService} from "../../services/error-formatter.service";



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  searchForm = this.formBuilder.group({
    type: [null],
    month: [null]
  });
  public searchType: string;
  public searchMonth: string;
  top10: TopTenEvents[];

  noEvents = false;
  constructor(private eventService: EventService, private formBuilder: FormBuilder,
              private toastService: ToastService,     private errorFormatterService: ErrorFormatterService) {
  }

  ngOnInit() {
    this.searchType = 'All Events';
    this.searchMonth = ((new Date()).getMonth() + 1 ) + '-' + ((new Date()).getFullYear());
    this.getTop10Events();
  }

  onSubmit(){
    this.eventService.getTopEvents(
        {
          typeId: this.searchForm.value.type === null ? null : this.searchForm.value.type.id,
          month: this.searchForm.value.month === null ? null : this.searchForm.value.month
        }
    ).subscribe({
      next: (top10: TopTenEvents[]) => {
        console.log(this.searchForm.value.type);
        console.log(this.searchForm.value.month);
        this.searchType = this.searchForm.value.type && typeof this.searchForm.value.type !== 'string'
          ? this.searchForm.value.type.name
          : 'All events';

        this.searchMonth = this.searchForm.value.month
          ? `${(new Date(this.searchForm.value.month).getMonth() + 1)}.${(new Date(this.searchForm.value.month).getFullYear())}`
          : `${(new Date()).getMonth() + 1}-${(new Date()).getFullYear()}`;

        this.top10 = top10;
        if(this.top10.length === 0) {
          this.noEvents = true;
        } else {
          this.noEvents = false;
          this.toastService.showSuccess('Success', 'The Top 10 events for chosen category and month are being displayed.');
        }
      },
      error: error => {
        this.toastService.showError('Error searching for categories', this.errorFormatterService.format(error['error'] as ErrorResponseDto));
      }
    });
  }


  private getTop10Events() {
    this.eventService.getTopEvents(
        {
          typeId: null,
          month: null
        }
    ).subscribe({
      next: (top10: TopTenEvents[]) => {
        this.top10 = top10;
      },
      error: error => {
        this.toastService.showError('Error retrieving top10 events', this.errorFormatterService.format(error['error'] as ErrorResponseDto));
      }
    });
  }

}

