import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileOrderDetailComponent } from './profile-order-detail.component';

describe('ProfileOrderDetailComponent', () => {
  let component: ProfileOrderDetailComponent;
  let fixture: ComponentFixture<ProfileOrderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileOrderDetailComponent]
    });
    fixture = TestBed.createComponent(ProfileOrderDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
