import { Component } from '@angular/core';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../../types/pageable-request';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { tap } from 'rxjs/operators';
import { NewsSearchDto } from '../../../dtos/news-search-dto';
import { NewsService } from '../../../services/news.service';
import { NewsListManagementDto } from '../../../dtos/news-list-management-dto';

@Component({
  selector: 'app-management-news',
  templateUrl: './management-news.component.html',
  styleUrls: ['./management-news.component.scss']
})
export class ManagementNewsComponent {
  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;
  public news$: Observable<NewsListManagementDto[]>;
  public searchAttributes$ = new BehaviorSubject<NewsSearchDto>({});
  private onPageChange$ = new BehaviorSubject<number>(0);

  constructor(
    private service: NewsService
  ) {
    this.news$ = combineLatest([this.searchAttributes$, this.onPageChangeDistinct$]).pipe(
      debounceTime(250),
      switchMap(([searchAttributes, page]) => {
        return this.service.findNews({
          ...searchAttributes
        }, { page, size: 20 });
      }),
      tap(page => {
        this.pageableState = {
          maxSize: 10,
          pageSize: 20,
          currentPage: page.currentPage + 1,
          totalPages: page.totalPages,
          totalElements: page.totalElements
        };
      }),
      map(page => page.content)
    );
  }

  private get onPageChangeDistinct$(): Observable<number> {
    return this.onPageChange$.pipe(
      distinctUntilChanged()
    );
  }

  onPageChange(newPage: number) {
    this.onPageChange$.next(newPage - 1);
  }

  public authorName(news: NewsListManagementDto): string {
    if (!news.authorLastName || !news.authorFirstName) {
      return '[deleted]';
    }

    return `${news.authorFirstName} ${news.authorLastName}`;
  }
}
