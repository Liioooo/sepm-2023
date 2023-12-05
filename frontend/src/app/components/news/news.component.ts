import { Component } from '@angular/core';
import { BehaviorSubject, distinctUntilChanged, map, merge, Observable, switchMap } from 'rxjs';
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
  public selectedRequestType: NewsReqType;
  private onPageChange$ = new BehaviorSubject<number>(0);
  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;

  constructor(
    private formBuilder: FormBuilder,
    private newsService: NewsService,
    private router: Router,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
    // Merge onPageChangeDistinct$ and requestType changes
    merge(this.onPageChangeDistinct$, this.selectedRequestType).pipe(
      switchMap((val: any) => {
        // Assuming `newsService.requestType$` is the BehaviorSubject holding requestType
        return this.newsService.getNewsList(val); // Call loadNews whenever either value changes
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
    ).subscribe((data: any) => {
      this.newsList$ = data;
    });
  }

  onPageChange(newPage: number) {
    this.onPageChange$.next(newPage - 1);
  }

  private get onPageChangeDistinct$(): Observable<number> {
    return this.onPageChange$.pipe(
      distinctUntilChanged()
    );
  }

  protected readonly NewsReqType = NewsReqType;

  setRequestType(reqType: NewsReqType) {
    this.selectedRequestType = reqType;
  }

}
