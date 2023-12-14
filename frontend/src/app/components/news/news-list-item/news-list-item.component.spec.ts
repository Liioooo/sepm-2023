import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsListItemComponent } from './news-list-item.component';

describe('NewsListItemComponent', () => {
  let component: NewsListItemComponent;
  let fixture: ComponentFixture<NewsListItemComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewsListItemComponent]
    });
    fixture = TestBed.createComponent(NewsListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
