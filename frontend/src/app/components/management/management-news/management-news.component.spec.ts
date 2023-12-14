import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagementNewsComponent } from './management-news.component';

describe('ManagementNewsComponent', () => {
  let component: ManagementNewsComponent;
  let fixture: ComponentFixture<ManagementNewsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementNewsComponent]
    });
    fixture = TestBed.createComponent(ManagementNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
