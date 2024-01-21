import { Component } from '@angular/core';
import { BehaviorSubject, combineLatest, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { NewsListDto } from '../../dtos/news-list-dto';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../types/pageable-request';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from '../../services/toast.service';
import { ErrorFormatterService } from '../../services/error-formatter.service';
import { NewsService } from '../../services/news.service';
import { NewsReqType } from '../../enums/newsReqType';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent {
  public newsList$: Observable<NewsListDto[]>;
  public selectedRequestType$: BehaviorSubject<NewsReqType>;
  private onPageChange$ = new BehaviorSubject<number>(0);
  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;

  constructor(
    private formBuilder: FormBuilder,
    private newsService: NewsService,
    private router: Router,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
    this.selectedRequestType$ = new BehaviorSubject<NewsReqType>(NewsReqType.Unread);

    this.newsList$ = combineLatest([this.selectedRequestType$, this.onPageChangeDistinct$]).pipe(
      switchMap(([requestType, page]) => {
        return this.newsService.getNewsList(
          requestType, { page, size: 20 }
        );
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

  protected readonly NewsReqType = NewsReqType;

  onPageChange(newPage: number) {
    this.onPageChange$.next(newPage - 1);
  }

  private get onPageChangeDistinct$(): Observable<number> {
    return this.onPageChange$.pipe(
      distinctUntilChanged()
    );
  }

  setRequestType(reqType: NewsReqType) {
    this.selectedRequestType$.next(reqType);
  }

}
