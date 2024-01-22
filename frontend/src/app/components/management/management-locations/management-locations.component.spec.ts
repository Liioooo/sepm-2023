import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagementLocationsComponent } from './management-locations.component';

describe('ManagementLocationsComponent', () => {
  let component: ManagementLocationsComponent;
  let fixture: ComponentFixture<ManagementLocationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementLocationsComponent]
    });
    fixture = TestBed.createComponent(ManagementLocationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
