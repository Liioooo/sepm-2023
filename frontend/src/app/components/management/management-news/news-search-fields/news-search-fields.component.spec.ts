import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsSearchFieldsComponent } from './news-search-fields.component';

describe('NewsSearchFieldsComponent', () => {
  let component: NewsSearchFieldsComponent;
  let fixture: ComponentFixture<NewsSearchFieldsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewsSearchFieldsComponent]
    });
    fixture = TestBed.createComponent(NewsSearchFieldsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
