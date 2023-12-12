import { Component } from '@angular/core';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../../types/pageable-request';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { tap } from 'rxjs/operators';
import { NewsDetailDto } from '../../../dtos/news-detail-dto';
import { NewsSearchDto } from '../../../dtos/news-search-dto';
import { NewsService } from '../../../services/news.service';

@Component({
  selector: 'app-management-news',
  templateUrl: './management-news.component.html',
  styleUrls: ['./management-news.component.scss']
})
export class ManagementNewsComponent {
  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;
  public news$: Observable<NewsDetailDto[]>;
  public searchAttributes$ = new BehaviorSubject<NewsSearchDto>({});
  private onPageChange$ = new BehaviorSubject<number>(0);

  constructor(
    private service: NewsService,
    private route: ActivatedRoute
  ) {
    this.news$ = combineLatest([this.searchAttributes$, this.searchTerm$, this.onPageChangeDistinct$]).pipe(
      debounceTime(250),
      switchMap(([searchAttributes, search, page]) => {
        return this.service.getNews({
          ...searchAttributes
        }, { page, size: 20 });
      }),
      tap(page => {
        this.pageableState = {
          pageSize: 20,
          currentPage: page.currentPage + 1,
          totalPages: page.totalPages,
          totalElements: page.totalElements
        };
      }),
      map(page => page.content)
    );
  }

  get searchTerm$(): Observable<string> {
    return this.route.queryParams.pipe(
      map(params => params.search)
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
}
