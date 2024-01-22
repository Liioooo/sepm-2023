import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { removeEmptyProps } from '../../../utils/removeEmptyProps';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { LocationSearchDto } from '../../../dtos/location-search-dto';

@Component({
  selector: 'app-location-search-fields',
  templateUrl: './location-search-fields.component.html',
  styleUrls: ['./location-search-fields.component.scss']
})
export class LocationSearchFieldsComponent {

  public form: FormGroup = new FormGroup({
    title: new FormControl(''),
    address: new FormControl(''),
    postalCode: new FormControl(''),
    city: new FormControl(''),
    country: new FormControl('')
  });

  @Output() searchChange = new EventEmitter<LocationSearchDto>();

  constructor() {
    this.form.valueChanges.pipe(
      takeUntilDestroyed()
    ).subscribe(value => this.handleFormChange(value));
  }

  handleFormChange(value: any) {
    if (this.form.invalid)
      return;

    this.searchChange.emit(removeEmptyProps(value));
  }

  clearSearchFields() {
    this.form.reset();
  }
}
