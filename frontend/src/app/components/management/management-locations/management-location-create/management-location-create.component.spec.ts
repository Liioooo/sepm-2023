import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagementLocationCreateComponent } from './management-location-create.component';

describe('ManagementLocationCreateComponent', () => {
  let component: ManagementLocationCreateComponent;
  let fixture: ComponentFixture<ManagementLocationCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementLocationCreateComponent]
    });
    fixture = TestBed.createComponent(ManagementLocationCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
