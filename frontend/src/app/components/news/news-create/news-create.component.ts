import { Component } from '@angular/core';
import { NewsCreateDto } from '../../../dtos/news-create-dto';
import { FormBuilder } from '@angular/forms';
import { NewsService } from '../../../services/news.service';
import { Router } from '@angular/router';
import { ToastService } from '../../../services/toast.service';
import { ErrorFormatterService } from '../../../services/error-formatter.service';

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

  // Function to handle file change
  onFileChanged(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.newsDto.image = input.files[0];
    }
  }

  // Function to handle form submission
  onSubmit() {
    this.newsService.createNews(this.newsDto);

    // Send this.newsDto to your backend service
    // You may need to use FormData to send the file along with other data
    const formData = new FormData();
    formData.append('title', this.newsDto.title);
    formData.append('overviewText', this.newsDto.overviewText);
    formData.append('text', this.newsDto.text);
    formData.append('image', this.newsDto.image);

    // Make API call to your Spring Boot backend
    // Example using fetch API
    fetch('YOUR_BACKEND_URL', {
      method: 'POST',
      headers: {
        Authorization: 'Bearer YOUR_ACCESS_TOKEN' // If authentication is required
      },
      body: formData
    })
      .then(response => {
        if (response.ok) {
          // Handle success
          console.log('News created successfully');
        } else {
          // Handle error
          console.error('Failed to create news');
        }
      })
      .catch(error => {
        console.error('Error creating news:', error);
      });
  }
}
