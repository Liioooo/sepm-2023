import { Component, EventEmitter, OnInit, Optional, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { EventSearchDto } from '../../../dtos/event-search-dto';

@Component({
  selector: 'app-event-search-fields',
  templateUrl: './event-search-fields.component.html',
  styleUrls: ['./event-search-fields.component.scss']
})
export class EventSearchFieldsComponent implements OnInit {

  public form: FormGroup = new FormGroup({
    artist: new FormControl(),
    title: new FormControl(),
    location: new FormControl(),
    timeStart: new FormControl(),
    timeEnd: new FormControl(),
    priceMax: new FormControl()
  });

  @Output() searchChange = new EventEmitter<EventSearchDto>();

  constructor() {
  }

  ngOnInit() {

  }

  handleFormChange() {
    if (this.form.invalid)
      return;

    this.form.value.timeStart = this.form.value.timeStart ? new Date(this.form.value.timeStart) : null;
    this.form.value.timeEnd = this.form.value.timeEnd ? new Date(this.form.value.timeEnd) : null;

    this.searchChange.emit(this.form.value);
  }
}
