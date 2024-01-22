import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { EventSearchDto } from '../../../dtos/event-search-dto';
import { NgbCalendar, NgbDate, NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { removeEmptyProps } from '../../../utils/removeEmptyProps';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-event-search-fields',
  templateUrl: './event-search-fields.component.html',
  styleUrls: ['./event-search-fields.component.scss']
})
export class EventSearchFieldsComponent {

  public form: FormGroup = new FormGroup({
    artist: new FormControl(''),
    title: new FormControl(''),
    priceMax: new FormControl(''),
    type: new FormControl(''),
    duration: new FormControl('')
  });

  hoveredDate: NgbDate | null = null;
  fromDate: NgbDate | null = null;
  toDate: NgbDate | null = null;

  @Output() searchChange = new EventEmitter<EventSearchDto>();

  constructor(private calendar: NgbCalendar, private dateAdapter: NgbDateAdapter<Date>) {
    this.form.valueChanges.pipe(
      takeUntilDestroyed()
    ).subscribe(value => this.handleFormChange(value));
  }

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date && date.after(this.fromDate)) {
      this.toDate = date;
    } else {
      this.toDate = null;
      this.fromDate = date;
    }

    this.handleFormChange(this.form.value);
  }

  isHovered(date: NgbDate) {
    return (
      this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate)
    );
  }

  isInside(date: NgbDate) {
    return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return (
      date.equals(this.fromDate) ||
      (this.toDate && date.equals(this.toDate)) ||
      this.isInside(date) ||
      this.isHovered(date)
    );
  }

  validateInput(input: string): NgbDate | null {
    const parsed = new Date(input);
    const ngbDate = this.dateAdapter.fromModel(parsed);
    return ngbDate && this.calendar.isValid(NgbDate.from(ngbDate)) ? NgbDate.from(ngbDate) : null;
  }

  formatDate(date: NgbDateStruct | null): string {
    return this.dateAdapter.toModel(date)?.toLocaleDateString([], {}) ?? '';
  }

  handleFormChange(value: any) {
    if (this.form.invalid)
      return;

    value.timeStart = value.timeStart ? new Date(this.form.value.timeStart) : null;
    value.timeEnd = value.timeEnd ? new Date(this.form.value.timeEnd) : null;

    const data = {
      ...value,
      timeStart: this.dateAdapter.toModel(this.fromDate),
      timeEnd: this.dateAdapter.toModel(this.toDate)
    };

    this.searchChange.emit(removeEmptyProps(data));
  }

  clearSearchFields() {
    this.form.reset();
    this.form.get('type').setValue('');
    this.fromDate = null;
    this.toDate = null;
    this.handleFormChange(this.form.value);
  }
}
