import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NgbDate, NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { removeEmptyProps } from '../../../../utils/removeEmptyProps';
import { NewsSearchDto } from '../../../../dtos/news-search-dto';

@Component({
  selector: 'app-news-search-fields',
  templateUrl: './news-search-fields.component.html',
  styleUrls: ['./news-search-fields.component.scss']
})
export class NewsSearchFieldsComponent {

  public form: FormGroup = new FormGroup({
    title: new FormControl(''),
    author: new FormControl(''),
    date: new FormControl('')
  });

  publishDate: NgbDate | null = null;

  @Output() searchChange = new EventEmitter<NewsSearchDto>();

  constructor(private dateAdapter: NgbDateAdapter<Date>) {
    this.form.valueChanges.pipe(
      takeUntilDestroyed()
    ).subscribe(value => this.handleFormChange(value));
  }

  formatDate(date: NgbDateStruct | null): string {
    return this.dateAdapter.toModel(date)?.toLocaleDateString([], {}) ?? '';
  }

  handleFormChange(value: any) {
    if (this.form.invalid)
      return;

    value.publishDate = value.publishDate ? new Date(this.form.value.publishDate) : null;

    const data = {
      ...value,
      date: this.dateAdapter.toModel(this.publishDate)
    };

    this.searchChange.emit(removeEmptyProps(data));
  }

}
