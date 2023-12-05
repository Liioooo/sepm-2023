import { Component, Input } from '@angular/core';
import { NewsListDto } from '../../../dtos/news-list-dto';

@Component({
  selector: 'app-news-list-item',
  templateUrl: './news-list-item.component.html',
  styleUrls: ['./news-list-item.component.scss']
})
export class NewsListItemComponent {

  @Input() news!: NewsListDto;

}
