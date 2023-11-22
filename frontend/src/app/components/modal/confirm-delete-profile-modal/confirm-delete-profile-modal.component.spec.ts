import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDeleteProfileModalComponent } from './confirm-delete-profile-modal.component';

describe('ConfirmDeleteProfileModalComponent', () => {
  let component: ConfirmDeleteProfileModalComponent;
  let fixture: ComponentFixture<ConfirmDeleteProfileModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfirmDeleteProfileModalComponent]
    });
    fixture = TestBed.createComponent(ConfirmDeleteProfileModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
