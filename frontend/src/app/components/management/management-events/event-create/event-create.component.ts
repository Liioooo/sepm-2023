import { Component, OnInit } from '@angular/core';
import { ToastService } from '../../../../services/toast.service';
import { Router } from '@angular/router';
import { EventService } from '../../../../services/event.service';
import { ErrorResponseDto } from '../../../../dtos/error-response-dto';
import { ErrorFormatterService } from '../../../../services/error-formatter.service';
import { EventCreateDto } from '../../../../dtos/event-create-dto';
import { ArtistDetailDto } from '../../../../dtos/artist-detail-dto';
import { HallRowsDetailDto } from '../../../../dtos/hall-rows-detail-dto';
import { LocationsService } from '../../../../services/location.service';
import { EventType } from '../../../../enums/eventType';

@Component({
  selector: 'app-event-create',
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.scss']
})
export class EventCreateComponent implements OnInit {

  event: EventCreateDto = new EventCreateDto();
  artists: ArtistDetailDto[];
  selectedArtistId: number;
  halls: HallRowsDetailDto[];
  selectedHallId: number;

  constructor(
    private notification: ToastService,
    private router: Router,
    private eventService: EventService,
    private locationService: LocationsService,
    private errorFormatterService: ErrorFormatterService) {
  }

  ngOnInit() {
    this.locationService.getAllHalls().subscribe({
      next: halls => {
        this.halls = halls;
        this.selectedHallId = this.halls[0].id;
      },
      error: error => {
        this.notification.showError('Error', this.errorFormatterService.format(error['error'] as ErrorResponseDto));
      }
    });
    this.eventService.getAllArtists().subscribe({
      next: artists => {
        this.artists = artists;
        this.selectedArtistId = this.artists[0].id;
      },
      error: error => {
        this.notification.showError('Error', this.errorFormatterService.format(error['error'] as ErrorResponseDto));
      }
    });

    this.event.type = EventType.UNKNOWN;
  }

  onArtistSelected(value: string): void {
    for (let i = 0; i < this.artists.length; i++) {
      if (this.artists[i].fictionalName != null) {
        if (this.artists[i].fictionalName.includes(value)) {
          this.selectedArtistId = this.artists[i].id;
        }
      }
      if (this.artists[i].firstname != null) {
        if (this.artists[i].firstname.includes(value)) {
          this.selectedArtistId = this.artists[i].id;
        }
      }
    }
  }

  onHallSelected(value: string): void {
    for (let i = 0; i < this.halls.length; i++) {
      if (value.includes(this.halls[i].name) && value.includes(this.halls[i].location.title)) {
        this.selectedHallId = this.halls[i].id;
      }
    }
  }

  onTypeSelected(value: string): void {
    switch (value) {
      case 'unknown':
        this.event.type = EventType.UNKNOWN;
        break;
      case 'concert':
        this.event.type = EventType.CONCERT;
        break;
      case 'show':
        this.event.type = EventType.SHOW;
        break;
      case 'festival':
        this.event.type = EventType.FESTIVAL;
        break;
      case 'sport':
        this.event.type = EventType.SPORT;
        break;
      case 'culture':
        this.event.type = EventType.CULTURE;
        break;
    }

  }

  onFileChanged(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.event.image = input.files[0];
    }
  }

  public onSubmit(): void {
    this.event.artistId = this.selectedArtistId;
    this.event.hallId = this.selectedHallId;

    this.eventService.createEvent(this.event).subscribe({
      next: data => {
        this.notification.showSuccess('Success', 'Event successfully created');
        this.router.navigate(['/management/events']);
      },
      error: err => {
        this.notification
          .showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
      }
    });
  }
}
