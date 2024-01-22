import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestPasswordChangeModalComponent } from './request-password-change-modal.component';

describe('RequestPasswordChangeModalComponent', () => {
  let component: RequestPasswordChangeModalComponent;
  let fixture: ComponentFixture<RequestPasswordChangeModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequestPasswordChangeModalComponent]
    });
    fixture = TestBed.createComponent(RequestPasswordChangeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
