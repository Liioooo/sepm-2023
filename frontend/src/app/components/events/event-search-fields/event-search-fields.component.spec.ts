import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EventSearchFieldsComponent } from './event-search-fields.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule } from '@angular/forms';

describe('EventSearchFieldsComponent', () => {
  let component: EventSearchFieldsComponent;
  let fixture: ComponentFixture<EventSearchFieldsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
      declarations: [EventSearchFieldsComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventSearchFieldsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
