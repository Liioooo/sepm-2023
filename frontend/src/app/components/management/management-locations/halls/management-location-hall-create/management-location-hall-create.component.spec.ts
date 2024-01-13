import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagementLocationHallCreateComponent } from './management-location-hall-create.component';

describe('ManagementLocationHallCreateComponent', () => {
  let component: ManagementLocationHallCreateComponent;
  let fixture: ComponentFixture<ManagementLocationHallCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementLocationHallCreateComponent]
    });
    fixture = TestBed.createComponent(ManagementLocationHallCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
