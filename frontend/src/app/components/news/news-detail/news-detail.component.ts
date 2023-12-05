import { Component, OnInit } from '@angular/core';
import { NewsDetailDto } from '../../../dtos/news-detail-dto';
import { NewsService } from '../../../services/news.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';

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
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.loadNewsDetailDto();
  }

  loadNewsDetailDto() {
    this.newsDetail$ = this.newsService.getNews(Number(this.route.snapshot.paramMap.get('id')));
  }

}
