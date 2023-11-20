import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagementEventsComponent } from './management-events.component';

describe('ManagementEventsComponent', () => {
  let component: ManagementEventsComponent;
  let fixture: ComponentFixture<ManagementEventsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementEventsComponent]
    });
    fixture = TestBed.createComponent(ManagementEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
