import { Component, OnInit } from '@angular/core';
import { ToastService } from '../../services/toast.service';
import { EventService } from '../../services/event.service';
import { FormBuilder } from '@angular/forms';
import { ErrorResponseDto } from '../../dtos/error-response-dto';
import { ErrorFormatterService } from '../../services/error-formatter.service';
import { EventWithBoughtCountDto } from '../../dtos/event-with-bought-count-dto';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  monthNames: string[] = [
    'JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE', 'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER'
  ];

  searchForm = this.formBuilder.group({
    type: 'SHOW',
    month: this.monthNames[new Date().getMonth()]
  });

  currentMonth: number = new Date().getMonth();
  searchMonth: string;
  top10: EventWithBoughtCountDto[];
  searchType: string;

  constructor(
    private eventService: EventService,
    private formBuilder: FormBuilder,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
  }

  ngOnInit() {
    this.setSearchMonth();
    this.getTop10Events();
    this.searchType = this.searchForm.value.type.charAt(0) + this.searchForm.value.type.slice(1).toLowerCase();
  }

  onSubmit() {

    this.getTop10Events();
    this.setSearchMonth();
    this.searchType = this.searchForm.value.type.charAt(0) + this.searchForm.value.type.slice(1).toLowerCase();

  }

  convertStringToNumber(): number {
    const wantedMonthNumber = this.monthNames.indexOf(this.searchForm.value.month);
    if (this.currentMonth <= wantedMonthNumber) {
      return wantedMonthNumber - this.currentMonth;
    }
    return 12 - (this.currentMonth - wantedMonthNumber);
  }

  setSearchMonth(): void {
    let month = this.searchForm.value.month;
    const wantedMonthNumber = this.monthNames.indexOf(this.searchForm.value.month);
    if (this.currentMonth <= wantedMonthNumber) {
      this.searchMonth = `${month.charAt(0) + month.slice(1).toLowerCase()} ${new Date().getFullYear()}`;
    } else {
      this.searchMonth = `${month.charAt(0) + month.slice(1).toLowerCase()} ${new Date().getFullYear() + 1}`;
    }
  }

  private getTop10Events() {
    this.eventService.getTopEvents(
      {
        type: this.searchForm.value.type === '' ? 'SHOW' : this.searchForm.value.type,
        month: this.searchForm.value.month === '' ? 0 : this.convertStringToNumber()
      }
    ).subscribe({
      next: (top10: EventWithBoughtCountDto[]) => {
        this.top10 = top10;
      },
      error: error => {
        this.toastService.showError('Error retrieving top10 events', this.errorFormatterService.format(error['error'] as ErrorResponseDto));
      }
    });
  }
}
