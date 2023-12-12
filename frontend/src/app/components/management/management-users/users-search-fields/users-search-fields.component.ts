import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { removeEmptyProps } from '../../../../utils/removeEmptyProps';
import { UserSearchDto } from '../../../../dtos/user-search-dto';

@Component({
  selector: 'app-users-search-fields',
  templateUrl: './users-search-fields.component.html',
  styleUrls: ['./users-search-fields.component.scss']
})
export class UsersSearchFieldsComponent {

  public form: FormGroup = new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    email: new FormControl('')
  });

  @Output() searchChange = new EventEmitter<UserSearchDto>();

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
