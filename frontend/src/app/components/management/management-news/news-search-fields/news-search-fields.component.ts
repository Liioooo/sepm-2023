import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
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
    authorName: new FormControl('')
  });

  @Output() searchChange = new EventEmitter<NewsSearchDto>();

  constructor() {
    this.form.valueChanges.pipe(
      takeUntilDestroyed()
    ).subscribe(value => this.handleFormChange(value));
  }

  handleFormChange(value: any) {
    if (this.form.invalid)
      return;

    const data = {
      ...value
    };

    this.searchChange.emit(removeEmptyProps(data));
  }

}
