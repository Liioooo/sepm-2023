import { Component, OnInit } from '@angular/core';
import { NewsDetailDto } from '../../../dtos/news-detail-dto';
import { NewsService } from '../../../services/news.service';
import { ActivatedRoute } from '@angular/router';
import { ErrorResponseDto } from '../../../dtos/error-response-dto';
import { ToastService } from '../../../services/toast.service';
import { ErrorFormatterService } from '../../../services/error-formatter.service';
import { catchError, Observable, of } from 'rxjs';

@Component({
  selector: 'app-news-detail',
  templateUrl: './news-detail.component.html',
  styleUrls: ['./news-detail.component.scss']
})
export class NewsDetailComponent implements OnInit {
  public newsDetail$: Observable<NewsDetailDto>;

  constructor(
    private newsService: NewsService,
    private route: ActivatedRoute,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
  }

  ngOnInit(): void {
    this.loadNewsDetailDto();
  }

  loadNewsDetailDto() {

    this.newsDetail$ = this.newsService.getNews(Number(this.route.snapshot.paramMap.get('id'))).pipe(
      catchError(err => {
        this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
        return of(undefined);
      })
    );
  }

}
