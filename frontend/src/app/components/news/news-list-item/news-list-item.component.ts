import { Component, Input, OnInit } from '@angular/core';
import { NewsListDto } from '../../../dtos/news-list-dto';

@Component({
  selector: 'app-news-list-item',
  templateUrl: './news-list-item.component.html',
  styleUrls: ['./news-list-item.component.scss']
})
export class NewsListItemComponent implements OnInit {

  @Input() public newsItem!: NewsListDto;

  ngOnInit(): void {
    console.log('NewsListItemComponent created');
  }

}
