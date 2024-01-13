import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagementUserCreateComponent } from './management-user-create.component';

describe('ManagementUserCreateComponent', () => {
  let component: ManagementUserCreateComponent;
  let fixture: ComponentFixture<ManagementUserCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementUserCreateComponent]
    });
    fixture = TestBed.createComponent(ManagementUserCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
