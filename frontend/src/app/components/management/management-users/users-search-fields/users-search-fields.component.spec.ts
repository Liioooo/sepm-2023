import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersSearchFieldsComponent } from './users-search-fields.component';

describe('UsersSearchFieldsComponent', () => {
  let component: UsersSearchFieldsComponent;
  let fixture: ComponentFixture<UsersSearchFieldsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UsersSearchFieldsComponent]
    });
    fixture = TestBed.createComponent(UsersSearchFieldsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
