import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDeleteReservationModalComponent } from './confirm-delete-reservation-modal.component';

describe('ConfirmDeleteProfileModalComponent', () => {
  let component: ConfirmDeleteReservationModalComponent;
  let fixture: ComponentFixture<ConfirmDeleteReservationModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfirmDeleteReservationModalComponent]
    });
    fixture = TestBed.createComponent(ConfirmDeleteReservationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
