import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventCheckoutComponent } from './event-checkout.component';

describe('EventCheckoutComponent', () => {
  let component: EventCheckoutComponent;
  let fixture: ComponentFixture<EventCheckoutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventCheckoutComponent]
    });
    fixture = TestBed.createComponent(EventCheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
