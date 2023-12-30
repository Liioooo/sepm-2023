import { Component } from '@angular/core';
import { NewsCreateDto } from '../../../dtos/news-create-dto';
import { FormBuilder } from '@angular/forms';
import { NewsService } from '../../../services/news.service';
import { Router } from '@angular/router';
import { ToastService } from '../../../services/toast.service';
import { ErrorFormatterService } from '../../../services/error-formatter.service';
import { ErrorResponseDto } from '../../../dtos/error-response-dto';

@Component({
  selector: 'app-news-create',
  templateUrl: './news-create.component.html',
  styleUrls: ['./news-create.component.scss']
})

export class NewsCreateComponent {
  newsDto: NewsCreateDto = new NewsCreateDto();

  constructor(
    private newsService: NewsService,
    private formBuilder: FormBuilder,
    private router: Router,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
  ) {
  }

  onFileChanged(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.newsDto.image = input.files[0];
    }
  }

  onSubmit() {
    this.newsService.createNews(this.newsDto).subscribe({
      next: () => {
        this.toastService.showSuccess('Success', 'News created successfully');
        this.router.navigate(['/management/news']);
      },
      error: err => this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto))
    });
  }
}
