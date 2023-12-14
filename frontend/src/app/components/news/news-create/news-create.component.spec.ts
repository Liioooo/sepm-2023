import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsCreateComponent } from './news-create.component';

describe('NewsCreateComponent', () => {
  let component: NewsCreateComponent;
  let fixture: ComponentFixture<NewsCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewsCreateComponent]
    });
    fixture = TestBed.createComponent(NewsCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
