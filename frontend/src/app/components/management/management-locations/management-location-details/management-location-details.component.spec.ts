import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagementLocationDetailsComponent } from './management-location-details.component';

describe('ManagementLocationDetailsComponent', () => {
  let component: ManagementLocationDetailsComponent;
  let fixture: ComponentFixture<ManagementLocationDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementLocationDetailsComponent]
    });
    fixture = TestBed.createComponent(ManagementLocationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
